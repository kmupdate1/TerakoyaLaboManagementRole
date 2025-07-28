import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import main.Dependencies

plugins {
    alias { libs.plugins.kotlin.jvm }
}

dependencies {
    implementation(Dependencies.Commons.VALIDATOR)
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.addAll("-Xannotation-default-target=param-property")
}
