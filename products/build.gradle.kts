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

val imageName = "products"
val registryIP = "localhost:5001"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:2.6.6")

    runtimeOnly("org.postgresql:postgresql")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

application {
    mainClass.set("de.baleipzig.products.ProductsApplication")
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
tasks.register<Exec>("redeployProducts") {
    group = "redeployment"
    dependsOn(":downloadKubectl", tasks.jib)

    val kubeCtl = rootProject.file("build/download/kubectl").absolutePath

    executable = kubeCtl
    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
        executable = "$executable.exe"
    }

    args("rollout", "restart", "deployment/products-deploy")

    doLast {
        exec {
            executable = kubeCtl
            args("rollout", "status", "deployment/products-deploy")
        }
    }
}

tasks.register("publishContainer") {
    group = "internal"
    dependsOn(tasks.clean, tasks.jib)
}
