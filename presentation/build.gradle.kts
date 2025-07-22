import org.gradle.api.internal.properties.GradleProperties
import org.gradle.initialization.GradlePropertiesController
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias { libs.plugins.kotlin.jvm }
    alias { libs.plugins.johnrengelman.shadow }
    id("application")
}

group = "jp.terakoyalabo"
version = "1.0-dev"

application {
    mainClass.set("jp.terakoyalabo.ApiServiceMainKt")
}

dependencies {
    implementation(Dependencies.Ktor.Server.CORE)
    implementation(Dependencies.Ktor.Server.CIO)
    implementation(Dependencies.Ktor.Server.STATUS_PAGES)
    implementation(Dependencies.Ktor.Server.OPENAPI)
    implementation(Dependencies.Ktor.Server.SWAGGER)

    implementation(Dependencies.GraphQL.Kotlin.KTOR_SERVER)

    implementation(Dependencies.Koin.CORE)
    implementation(Dependencies.Koin.KTOR)
    implementation(Dependencies.Koin.LOGGER_SLF4J)

    implementation(Dependencies.KtorApiGateway.Kmupdate1.CORE)
    implementation(Dependencies.KtorApiGateway.Kmupdate1.GCP)

    implementation(Dependencies.Logback.CLASSIC)

    implementation(project(Projects.COMMON))
    implementation(project(Projects.APPLICATION))
    implementation(project(Projects.INFRASTRUCTURE))
}

kotlin {
    jvmToolchain(23)
}

tasks.apply {
    shadowJar {
        manifest {
            attributes["Main-Class"] = application.mainClass.get()
        }
        archiveBaseName.set("service-api-travel")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}
