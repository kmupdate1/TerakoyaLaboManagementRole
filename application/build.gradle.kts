import main.Dependencies
import main.Projects

plugins {
    alias { libs.plugins.kotlin.jvm }
}

dependencies {
    implementation(Dependencies.GraphQL.Kotlin.KTOR_SERVER)

    implementation(project(Projects.COMMON))
}
