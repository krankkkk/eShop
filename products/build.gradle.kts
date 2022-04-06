import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
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
    implementation("org.springframework.data:spring-data-jpa:2.6.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // https://mvnrepository.com/artifact/com.h2database/h2
    implementation("com.h2database:h2:2.1.210")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

application {
    mainClass.set("de.baleipzig.products.ProductsApplication")
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
tasks.register<Exec>("redeployProducts") {
    group = "redeployment"
    dependsOn(":downloadKubectl", pushLatest)

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

tasks.bootJar {
    mainClass.set("de.baleipzig.products.ProductsApplication")
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
