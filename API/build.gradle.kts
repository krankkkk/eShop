plugins {
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.2.1"
    java
}

repositories {
    mavenCentral()
}

dependencies {


    // https://mvnrepository.com/artifact/javax.validation/validation-api
    implementation("javax.validation:validation-api:2.0.1.Final")
}
