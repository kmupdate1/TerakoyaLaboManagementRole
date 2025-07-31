package jp.terakoyalabo.infrastructure.web.interaction

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QuerySnapshot
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.infrastructure.web.common.dto.PermissionsDto

class PermissionInteraction(
    firestore: Firestore,
): WebInteraction(
    firestore = firestore, collectionName = "permissions",
) {
    fun create(document: PermissionsDto): ApiFuture<DocumentReference?> = createWithTransaction(
        document = document,
    ) { snapshots ->
        if (snapshots.size() > 0)
            throw DocumentCreateFailedException("Already document exists.")

        collection.document(document.identifier)
    }

    fun findByIdentifier(identifier: String): ApiFuture<QuerySnapshot?> =
        collection
            .whereEqualTo("identifier", identifier)
            .whereEqualTo("disabled", false).get()

    fun findByName(name: String): ApiFuture<QuerySnapshot?> =
        collection
            .whereEqualTo("name", name)
            .whereEqualTo("disabled", false).get()

    fun findAll(): QuerySnapshot? = collection.get().get()

    fun update(document: PermissionsDto): ApiFuture<DocumentReference?> {
        val fields = mapOf(
            "name" to document.name,
            "displayName" to document.displayName,
            "description" to document.description,
            "updatedAt" to document.updatedAt,
            "updatedBy" to document.updatedBy,
        )

        return updateWithTransaction(document = document, fields = fields) { snapshots ->
            if (snapshots.size() < 1)
                throw DocumentUpdateFailedException("No document found for update.")

            collection.document(document.identifier)
        }
    }

    fun softDelete(identifier: String, deletedAt: Long, userId: String): ApiFuture<DocumentReference?> = softDeleteWithTransaction(
        identifier = identifier,
        deletedAt = deletedAt,
        userId = userId,
    ) { snapshots ->
        if (snapshots.size() < 1)
            throw DocumentDeleteFailedException("No document found for delete logically.")

        collection.document(identifier)
    }

    fun hardDelete(identifier: String): ApiFuture<DocumentReference?> = hardDeleteWithTransaction(
        identifier = identifier,
    ) { snapshots ->
        if (snapshots.size() < 1)
            throw DocumentDeleteFailedException("No document found for delete physically.")

        collection.document(identifier)
    }
}
