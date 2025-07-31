package jp.terakoyalabo.module

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.Application
import org.litote.kmongo.json

fun Application.configureFirebase() {
    val gcpServiceConfig = environment.config.config("service.gcp")
    val fbServiceConfig = environment.config.config("service.firebase")
    val keyFile = gcpServiceConfig.property("key").getString()

    if (FirebaseApp.getApps().isEmpty()) {
        val serviceAccount = environment.classLoader.getResourceAsStream(keyFile)
            ?: throw IllegalStateException("$keyFile not found in resources.")

        val options = FirebaseOptions.builder().apply {
            setCredentials(GoogleCredentials.fromStream(serviceAccount))
        }.build()

        val initializer = FirebaseApp.initializeApp(options)

        environment.log.info("Firebase Admin SDK has initialized: ${initializer.json}")
    }
}
