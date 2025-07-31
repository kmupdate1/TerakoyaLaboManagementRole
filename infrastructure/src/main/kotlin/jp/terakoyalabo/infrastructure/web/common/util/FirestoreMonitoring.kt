package jp.terakoyalabo.infrastructure.web.common.util

import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.Firestore

class FirestoreMonitoring(
    private val delegate: Firestore
): Firestore by delegate {
    override fun collection(p0: String): CollectionReference {
        TODO("Not yet implemented")
    }
}
