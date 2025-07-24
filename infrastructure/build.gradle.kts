plugins {
    alias { libs.plugins.kotlin.jvm }
}

dependencies {
    implementation(Dependencies.MongoDB.DRIVER_CORE)
    implementation(Dependencies.MongoDB.DRIVER_SYNC)
    implementation(Dependencies.MongoDB.BSON)
    implementation(Dependencies.MongoDB.BSON_KOTLINX)

    implementation(Dependencies.KMongo.KMONGO)

    implementation(Dependencies.Google.Firebase.ADMIN)

    implementation(project(Projects.COMMON))
}
