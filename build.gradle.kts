import de.undercouch.gradle.tasks.download.Download
import org.apache.tools.ant.taskdefs.condition.Os.*
import java.io.ByteArrayOutputStream

plugins {
    id("de.undercouch.download") version "5.0.4"
}

val registryName = "local-registry"
val clusterName = "eshop-cluster"

var kindPath: String = project.file("build/download/kind").absolutePath
var ctlPath: String = project.file("build/download/kubectl").absolutePath

/**
 * Lädt die "Kind" application runter
 */
val downloadKind = tasks.register<Download>("downloadKind") {
    group = "internal"

    val type = if (isFamily(FAMILY_MAC)) {
        "darwin"
    } else if (isFamily(FAMILY_UNIX)) {
        "linux"
    } else {
        kindPath = "$kindPath.exe"
        "windows"
    }

    src("https://kind.sigs.k8s.io/dl/v0.13.0/kind-$type-amd64")
    dest(kindPath)

    doLast {
        if (type != "windows") {
            exec {
                executable = "chmod"
                args("+x", kindPath)
            }
        }
    }

    onlyIf {
        !project.file(kindPath).exists()
    }
}

/**
 * Lädt das CLI-Program für Kubernetes herunter
 */
val downloadKubectl = tasks.register<Download>("downloadKubectl") {
    group = "internal"

    val type = if (isFamily(FAMILY_MAC)) {
        "darwin"
    } else if (isFamily(FAMILY_UNIX)) {
        "linux"
    } else {
        ctlPath = "$ctlPath.exe"
        "windows"
    }

    if (type == "windows") {
        src("https://dl.k8s.io/release/v1.24.0/bin/windows/amd64/kubectl.exe")
    } else {
        src("https://dl.k8s.io/release/v1.24.0/bin/$type/amd64/kubectl")
    }

    dest(ctlPath)

    doLast {
        if (type != "windows") {
            exec {
                executable = "chmod"
                args("+x", ctlPath)
            }
        }
    }

    onlyIf {
        !project.file(ctlPath).exists()
    }
}

/**
 * Konfiguriert die Ingress Implementiertung "NGINX"
 */
val configureNGINX = tasks.register<Exec>("configureNGINX") {
    group = "internal"
    dependsOn(createKindCluster, downloadKubectl)

    executable = ctlPath
    args("apply", "-f", project.file("kubernetes/deploy.yaml").absolutePath)
}

/**
 * Erstellt eine lokale Docker-Registry, in dem unsere Images landen
 */
val createRegistry = tasks.register<Exec>("createRegistry") {
    group = "internal"

    executable = "docker"
    args("run", "-d", "-p", "127.0.0.1:5001:5000", "--restart=always", "--name", registryName, "registry:2")

    //Checks if a registry is already registered
    onlyIf {
        val out = ByteArrayOutputStream()
        exec {
            executable = "docker"
            args("container", "ls")
            standardOutput = out
        }

        !String(out.toByteArray()).contains(registryName)
    }
}

/**
 * Erstellt den Kubernetes Cluster via Kind und lädt grundlegende Konfigurationen
 */
val createKindCluster = tasks.register<Exec>("createKindCluster") {
    dependsOn(downloadKind, downloadKubectl, createRegistry)
    group = "internal"

    val configPath = project.file("kubernetes/kind_config.yaml").absolutePath

    executable = kindPath
    args("create", "cluster", "--name", clusterName, "--config", configPath)

    doLast {
        exec {
            executable = ctlPath
            args("apply", "-f", project.file("kubernetes/kind_configmap.yaml").absolutePath)
        }

        exec {
            executable = ctlPath
            args("label", "nodes", "eshop-cluster-worker", "ingress-ready=true")
        }
    }
}

/**
 * Verbindet das Netzwerk des Clusters mit dem Netzwerk unserer lokalen Registry
 */
val kindNetwork = tasks.register<Exec>("connectKindToKindNetwork") {
    dependsOn(createKindCluster)
    group = "internal"

    executable = "docker"
    args("network", "connect", "kind", registryName)
}

/**
 * Erstellt den kompletten Kubernetes Cluster
 */
tasks.register("createCluster") {
    dependsOn(pushImages, kindNetwork, configureNGINX, downloadKubectl)
    group = "deployment"

    doLast {
        exec {
            executable = ctlPath
            args("apply", "-f", project.file("kubernetes/postgres.yaml").absolutePath)
        }

        exec {
            executable = ctlPath
            args("rollout", "status", "deployment/postgres")
        }

        exec {
            executable = ctlPath
            args("apply", "-f", project.file("kubernetes/price.yaml").absolutePath)
        }

        exec {
            executable = ctlPath
            args("apply", "-f", project.file("kubernetes/products.yaml").absolutePath)
        }

        exec {
            executable = ctlPath
            args("apply", "-f", project.file("kubernetes/frontend.yaml").absolutePath)
        }

        try {
            exec {
                executable = ctlPath
                args("apply", "-f", project.file("kubernetes/ingress.yaml").absolutePath)
            }
        }catch(ignored :Throwable){
            exec {
                executable = ctlPath
                args("delete", "-A", "ValidatingWebhookConfiguration", "ingress-nginx-admission")
            }

            //If you at first don't succeed, just try again
            //this legit works, sometimes the validation Webhook just decides it doesn't want to work today
            exec {
                executable = ctlPath
                args("apply", "-f", project.file("kubernetes/ingress.yaml").absolutePath)
            }
        }
    }
}

/**
 * Entfernt die lokale Registry
 */
val deleteRegistry = tasks.register<Exec>("deleteRegistry") {
    group = "internal"

    executable = "docker"
    args("stop", registryName)

    doLast {
        exec {
            executable = "docker"
            args("rm", registryName)
        }
    }
}

/**
 * Entfernt den Kubernetes Cluster
 */
val deleteCluster = tasks.register<Exec>("deleteCluster") {
    dependsOn(deleteRegistry, downloadKind)
    group = "deployment"

    executable = kindPath
    args("delete", "cluster", "--name", clusterName)
}

/**
 * Pusht alle Docker-Container in die lokale Registry (Sammeltask)
 */
val pushImages = tasks.register("pushImages") {
    dependsOn(":products:publishContainer", ":prices:publishContainer", ":frontend:dockerPush")
    group = "internal"
}
