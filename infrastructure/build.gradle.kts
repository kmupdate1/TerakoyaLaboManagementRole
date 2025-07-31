import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import main.Dependencies
import main.Projects
import test.TestDependencies

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

    implementation(Dependencies.Slf4j.API)

    implementation(project(Projects.COMMON))

    testImplementation(TestDependencies.Junit.JUPITER_API)
    testImplementation(TestDependencies.Junit.JUPITER_ENGINE)
    testImplementation(TestDependencies.KMongo.KMONGO)
    testImplementation(TestDependencies.MOCKK)
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}
