import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.2.1"
    application
    java
}

group = "de.baleipzig"
version = "0.0.1-SNAPSHOT"

val imageName = "prices"
val registryIP = "localhost:5001"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":API"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("com.h2database:h2")
}

application {
    mainClass.set("de.baleipzig.prices.PricesApplication")
}

/**
 * Baut & Pusht den Container in die Registry
 */
jib {
    from {
        image = "eclipse-temurin:17-jre-alpine"
    }

    to {
        image = "$registryIP/$imageName"
        tags = mutableSetOf("latest", version.toString())
    }

    setAllowInsecureRegistries(true)
}

tasks.compileJava {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    mainClass.set("de.baleipzig.products.ProductsApplication")
}

tasks.jib {
    dependsOn(":createRegistry")//Die Registry sollte hier schon existieren, ansonsten gibts nichts zum hin pushen
}

/**
 * Pusht die neu gebaute Version in das Cluster
 */
tasks.register<Exec>("redeployPrice") {
    group = "redeployment"
    dependsOn(":downloadKubectl", tasks.jib)

    val kubeCtl = rootProject.file("build/download/kubectl").absolutePath

    executable = kubeCtl
    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
        executable = "$executable.exe"
    }

    args("rollout", "restart", "deployment/price-deploy")

    doLast {
        exec {
            executable = kubeCtl
            args("rollout", "status", "deployment/price-deploy")
        }
    }
}

tasks.register("publishContainer") {
    group = "internal"
    dependsOn(tasks.clean, tasks.jib)
}
