package jp.terakoyalabo.configuration

import com.google.firebase.FirebaseApp
import io.ktor.server.application.Application

fun Application.configureFirebase() {
    if (FirebaseApp.getApps().isEmpty()) {
        val serviceAccount = environment.classLoader.getResourceAsStream("service_account_key.json")
    }
}
