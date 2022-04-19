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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

application {
    mainClass.set("de.baleipzig.prices.PricesApplication")
}

/**
 * Baut den eigentlichen Docker-Container
 */
val buildContainer = tasks.register<Exec>("buildContainer") {
    group = "internal"
    dependsOn("bootJar")

    executable = "docker"
    args("build", "-t", "$imageName:$version", ".")
}

/**
 * Pusht den gebauten Docker-Container mit dem Tag "latest"
 */
val pushLatest = tasks.register<Exec>("pushLatest") {
    group = "internal"
    dependsOn(buildContainer)

    executable = "docker"
    args("tag", "$imageName:$version", "$registryIP/$imageName:latest")

    doLast {
        exec {
            executable = "docker"
            args("push", "$registryIP/$imageName:latest")
        }
    }
}

/**
 * Pusht die neu gebaute Version in das Cluster
 */
tasks.register<Exec>("redeployPrices") {
    group = "redeployment"
    dependsOn(":downloadKubectl", pushLatest)

    val kubeCtl = rootProject.file("build/download/kubectl").absolutePath

    executable = kubeCtl
    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
        executable = "$executable.exe"
    }

    args("rollout", "restart", "deployment/prices-deploy")

    doLast {
        exec {
            executable = kubeCtl
            args("rollout", "status", "deployment/prices-deploy")
        }
    }
}

tasks.bootJar {
    mainClass.set("de.baleipzig.prices.PricesApplication")
}

tasks.register("publishContainer") {
    group = "internal"
    dependsOn("clean", pushLatest)
}


tasks.compileJava {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

tasks.test {
    useJUnitPlatform()
}