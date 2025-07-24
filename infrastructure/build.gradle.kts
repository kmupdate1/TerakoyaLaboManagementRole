import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    implementation(Dependencies.Koin.CORE)

    implementation(Dependencies.Slf4j.API)

    implementation(project(Projects.COMMON))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}
