import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import main.Dependencies
import main.Projects

plugins {
    alias { libs.plugins.kotlin.jvm }
    alias { libs.plugins.kotlin.plugin.serialization }
    alias { libs.plugins.johnrengelman.shadow }
    id("application")
}

group = "jp.terakoyalabo"
version = "1.0-dev"

application {
    mainClass.set("jp.terakoyalabo.ApiServiceMainKt")
}

dependencies {
    implementation(Dependencies.Kotlinx.Serialization.JSON)

    implementation(Dependencies.Ktor.Server.CORE)
    implementation(Dependencies.Ktor.Server.CIO)
    implementation(Dependencies.Ktor.Server.STATUS_PAGES)

    implementation(Dependencies.GraphQL.Kotlin.KTOR_SERVER)

    implementation(Dependencies.MongoDB.DRIVER_CORE)
    implementation(Dependencies.KMongo.KMONGO)

    implementation(Dependencies.Koin.CORE)
    implementation(Dependencies.Koin.KTOR)
    implementation(Dependencies.Koin.LOGGER_SLF4J)

    // implementation(Dependencies.KtorApiGateway.Lax256.CORE)
    // implementation(Dependencies.KtorApiGateway.Lax256.GCP)
    implementation(Dependencies.KtorApiGateway.Kmupdate1.CORE)
    implementation(Dependencies.KtorApiGateway.Kmupdate1.GCP)

    implementation(Dependencies.Logback.CLASSIC)

    implementation(project(Projects.COMMON))
    implementation(project(Projects.INFRASTRUCTURE))
    implementation(project(Projects.APPLICATION))
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

tasks.apply {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        manifest {
            attributes["Main-Class"] = application.mainClass.get()
        }
        archiveBaseName.set("service-api-travel")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}
