import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("org.springframework.boot") version "3.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "11.4.2"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.9.10"
}

group = "com.prometheus.lesson"
version = "v0.1.0-beta.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

noArg {
    invokeInitializers = true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    /*
        Model Mapping: Automates mapping between DTOs and entities.
    */
    implementation("org.modelmapper:modelmapper:2.4.4")
    implementation("com.google.code.gson:gson:2.10.1")

    /*
       Spring Doc: Generates API documentation.
   */
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
}

tasks.withType<KotlinCompile> {
    dependsOn(tasks.ktlintFormat)
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
