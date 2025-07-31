package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Updates.set
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.infrastructure.database.common.dto.CoreInformationCollection
import org.litote.kmongo.combine

class CoreInformationInteraction(
    override val database: MongoDatabase,
    client: MongoClient,
): DatabaseInteraction(
    client = client,
    database = database,
    collectionName = "core_information",
) {
    fun createCoreInformation(information: CoreInformationCollection): InsertOneResult? = transaction { session ->
        if (getDocument(session = session, userId = information.userId) != null)
            throw DocumentCreateFailedException("Document already exists. Cannot create.")

        collection.insertOne(session, information)
    }

    fun referenceCoreInformation(userId: String): CoreInformationCollection? = transaction { session ->
        getDocument(session = session, userId = userId)
    }

    fun updateCoreInformation(information: CoreInformationCollection): UpdateResult? = transaction { session ->
        if (getDocument(session = session, userId = information.userId) == null)
            throw DocumentUpdateFailedException("No document found for update.")

        collection.updateOne(
            session,
            enabledFilter(userId = information.userId),
            combine(
                set("sign_in_provider", information.signInProvider),
                set("email_verified", information.emailVerified),
                set("updated_at", information.updatedAt),
                set("updated_by", information.updatedBy),
            ),
        )
    }

    fun deleteLogicallyCoreInformation(userId: String, updatedAt: Long): UpdateResult? = transaction { session ->
        if (getDocument(session = session, userId = userId) == null)
            throw DocumentDeleteFailedException("No document found for delete logically.")

        collection.updateOne(
            session,
            enabledFilter(userId = userId),
            combine(
                set("disabled", true),
                set("updated_at", updatedAt),
                set("updated_by", userId),
            ),
        )
    }

    fun deletePhysicallyCoreInformation(userId: String): DeleteResult? = transaction { session ->
        if (getDisabledDocument(session = session, userId = userId) == null)
            throw DocumentDeleteFailedException("No document found for delete physically.")

        collection.deleteOne(session, disabledFilter(userId = userId))
    }

    private val collection = getCollection<CoreInformationCollection>()
    private fun getDocument(session: ClientSession, userId: String) =
        collection.find(session, enabledFilter(userId = userId)).firstOrNull()
    private fun getDisabledDocument(session: ClientSession, userId: String) =
        collection.find(session, disabledFilter(userId = userId)).firstOrNull()
}
