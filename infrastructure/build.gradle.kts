plugins {
    alias { libs.plugins.kotlin.jvm }
}

dependencies {
    implementation(Dependencies.KMongo.KMONGO)
    implementation(Dependencies.Google.Firebase.ADMIN)

    implementation(project(Projects.COMMON))
}
