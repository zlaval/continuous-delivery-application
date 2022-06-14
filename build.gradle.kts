import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.2.1"
    id("maven-publish")
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.zlrx.blog"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib{
    from {
        image = "amazoncorretto:17.0.3-alpine3.15"
        auth {
            username = System.getenv("DOCKER_USER")
            password = System.getenv("DOCKER_PASSWORD")
        }
    }
    to {
        image = "zalerix/continuous-delivery-example"
        tags = setOf("latest", "${project.version}")
        auth {
            username = System.getenv("DOCKER_USER")
            password = System.getenv("DOCKER_PASSWORD")
        }
    }
    container {
        mainClass = "com.zlrx.blog.githubactionk8scd.GithubActionK8sCdApplicationKt"
        ports = listOf("8080/tcp", "9000/tcp")
        appRoot = "/app"
        workingDirectory = "/app"
        creationTime = "USE_CURRENT_TIMESTAMP"
        jvmFlags = listOf(
            "-XX:InitialRAMPercentage=40.0",
            "-XX:MaxRAMPercentage=75.0",
        )
    }
}