plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("io.qameta.allure:allure-junit5:2.24.0")
    testImplementation("ch.qos.logback:logback-classic:1.2.12")
}

tasks.test {
    useJUnitPlatform()
}