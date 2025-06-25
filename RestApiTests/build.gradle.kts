plugins {
    id("java")
    id("org.openapi.generator") version "7.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    named("main") {
        java {
            srcDir("$buildDir/generated-sources/swagger/src/main")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30") // ← обязательно для генерации

    testImplementation("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("io.rest-assured:kotlin-extensions:5.5.5")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.2.0")
    implementation("com.google.code.gson:gson:2.7")
    implementation("joda-time:joda-time:2.12.1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-junit5:2.25.0")
}

tasks.test {
    useJUnitPlatform()
}

val openApiGenerateFront by tasks.registering(org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    generatorName.set("java")
    remoteInputSpec.set("https://sb2frontend-altenar2-stage.biahosted.com/swagger/v1/swagger.json")
    outputDir.set("$buildDir/generated-sources/swagger")
    invokerPackage.set("")
    templateDir.set("$projectDir/src/test/resources/templates")
    apiPackage.set("com.altenar.sb2.frontend.api")
    modelPackage.set("com.altenar.sb2.frontend.model")

    importMappings.set(
        mapOf(
            "FeedProvidersEnum" to "com.altenar.sb2.frontend.model.FeedProvidersEnum"
        )
    )

    configOptions.set(
        mapOf(
            "dateLibrary" to "joda",
            "serializationLibrary" to "jackson",
            "interfaceOnly" to "true",
            "additionalModelTypeAnnotation" to "@lombok.Data"
        )
    )

    library.set("rest-assured")
    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)

    globalProperties.set(
        mapOf("models" to "")
    )
}

val openApiGenerateAdmin by tasks.registering(org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    generatorName.set("java")
    remoteInputSpec.set("https://sb2admin-altenar2-stage.biahosted.com/swagger/v1/swagger.json")
    outputDir.set("$buildDir/generated-sources/swagger")
    invokerPackage.set("")
    templateDir.set("$projectDir/src/test/resources/templates")
    apiPackage.set("com.altenar.sb2.admin.api")
    modelPackage.set("com.altenar.sb2.admin.model")

    importMappings.set(
        mapOf(
            "FeedProvidersEnum" to "com.altenar.sb2.admin.model.FeedProvidersEnum"
        )
    )

    configOptions.set(
        mapOf(
            "dateLibrary" to "joda",
            "serializationLibrary" to "jackson",
            "interfaceOnly" to "true",
            "additionalModelTypeAnnotation" to "@lombok.Data"
        )
    )

    library.set("rest-assured")
    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)

    globalProperties.set(
        mapOf("models" to "")
    )
}

tasks.register<GradleBuild>("cleanBuildPublish") {
    tasks = listOf("openApiGenerateFront", "openApiGenerateAdmin")
}

tasks.named("compileJava") {
    dependsOn("cleanBuildPublish")
}
