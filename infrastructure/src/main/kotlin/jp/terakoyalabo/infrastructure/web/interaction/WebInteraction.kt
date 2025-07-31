package jp.terakoyalabo.infrastructure.web.interaction

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QuerySnapshot
import jp.terakoyalabo.infrastructure.web.common.dto.PermissionsDto

open class WebInteraction(
    private val firestore: Firestore,
    private val collectionName: String,
) {
    protected val collection get() = firestore.collection(collectionName)

    protected fun createWithTransaction(
        document: PermissionsDto, disabled: Boolean = false,
        block: (QuerySnapshot) -> DocumentReference,
    ): ApiFuture<DocumentReference?> = firestore.runTransaction { transaction ->
        val snapshots = transaction.get(
            collection
                .whereEqualTo("name", document.name)
                .whereEqualTo("disabled", disabled)
        ).get()

        try {
            val result = block(snapshots)
            transaction.set(result, document)

            result
        } catch (e: Exception) { throw e }
    }

    protected fun updateWithTransaction(
        document: PermissionsDto, fields: Map<String, *>, disabled: Boolean = false,
        block: (QuerySnapshot) -> DocumentReference,
    ): ApiFuture<DocumentReference?> = firestore.runTransaction { transaction ->
        val snapshots = transaction.get(
            collection
                .whereEqualTo("identifier", document.identifier)
                .whereEqualTo("disabled", disabled)
        ).get()

        try {
            val result = block(snapshots)
            transaction.update(result, fields)

            result
        } catch (e: Exception) { throw e }
    }

    protected fun softDeleteWithTransaction(
        identifier: String, deletedAt: Long, userId: String, disabled: Boolean = false,
        block: (QuerySnapshot) -> DocumentReference,
    ): ApiFuture<DocumentReference?> = firestore.runTransaction { transaction ->
        val snapshots = transaction.get(
            collection
                .whereEqualTo("identifier", identifier)
                .whereEqualTo("disabled", disabled)
        ).get()

        try {
            val result = block(snapshots)
            transaction.update(
                result,
                mapOf(
                    "disabled" to true,
                    "updatedAt" to deletedAt,
                    "updatedBy" to userId,
                )
            )

            result
        } catch (e: Exception) { throw e }
    }

    protected fun hardDeleteWithTransaction(
        identifier: String, disabled: Boolean = true,
        block: (QuerySnapshot) -> DocumentReference,
    ): ApiFuture<DocumentReference?> = firestore.runTransaction { transaction ->
        val snapshots = transaction.get(
            collection
                .whereEqualTo("identifier", identifier)
                .whereEqualTo("disabled", disabled)
        ).get()

        try {
            val result = block(snapshots)
            transaction.delete(result)

            result
        } catch (e: Exception) { throw e }
    }
}
