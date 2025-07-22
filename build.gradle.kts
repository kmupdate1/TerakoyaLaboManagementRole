plugins {
    alias { libs.plugins.kotlin.jvm } apply false
    alias { libs.plugins.kotlin.plugin.serialization } apply false
}

allprojects {
    repositories {
        mavenLocal()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
        google()
    }
}
