val junitVersion = "5.10.0"
val kafkaClientsVersion = "4.0.0"
val jdbi3Version = "3.49.3"
val postgresVersion = "42.7.3"
val jacksonVersion = "2.17.0"
val awaitilityVersion = "4.2.1"
val slf4jVersion = "2.0.13"

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-junit5:2.24.0")

    testImplementation("org.apache.kafka:kafka-clients:$kafkaClientsVersion")

    testImplementation("org.jdbi:jdbi3-core:$jdbi3Version")
    testImplementation("org.jdbi:jdbi3-postgres:$jdbi3Version")
    testImplementation("org.postgresql:postgresql:$postgresVersion")

    testImplementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    testImplementation("org.awaitility:awaitility:$awaitilityVersion")

    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks.test {
    useJUnitPlatform()
}