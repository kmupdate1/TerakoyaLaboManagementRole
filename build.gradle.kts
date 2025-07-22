plugins {
    alias { libs.plugins.kotlin.jvm } apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven(url = "https://jitpack.io")
        google()
    }
}
