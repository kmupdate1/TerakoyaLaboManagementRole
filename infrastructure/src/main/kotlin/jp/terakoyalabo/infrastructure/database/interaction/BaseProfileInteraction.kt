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
import jp.terakoyalabo.infrastructure.database.common.dto.BaseProfileCollection
import org.litote.kmongo.combine

class BaseProfileInteraction(
    override val database: MongoDatabase,
    client: MongoClient,
): DatabaseInteraction(
    client = client,
    database = database,
    collectionName = "base_profile",
) {
    fun createBaseProfile(profile: BaseProfileCollection): InsertOneResult? = transaction { session ->
        if (getDocument(session = session, userId = profile.userId) != null)
            throw DocumentCreateFailedException("An active document with the specified information already exists. Cannot create a new document.")

        collection.insertOne(session, profile)
    }

    fun referenceBaseProfile(userId: String): BaseProfileCollection? = transaction { session ->
        getDocument(session = session, userId = userId)
    }

    fun updateBaseProfile(profile: BaseProfileCollection): UpdateResult? = transaction { session ->
        if (getDocument(session = session, userId = profile.userId) == null)
            throw DocumentUpdateFailedException("No document found for update.")

        collection.updateOne(
            session,
            enabledFilter(userId = profile.userId),
            combine(
                set("first_name", profile.firstName),
                set("first_name_kana", profile.firstNameKana),
                set("family_name", profile.familyName),
                set("family_name_kana", profile.familyNameKana),
                set("display_name", profile.displayName),
                set("updated_at", profile.updatedAt),
                set("updated_by", profile.updatedBy),
            ),
        )
    }

    fun deleteLogicallyBaseProfile(userId: String, updatedAt: Long): UpdateResult? = transaction { session ->
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

    fun deletePhysicallyBaseProfile(userId: String): DeleteResult? = transaction { session ->
        if (getDisabledDocument(session = session, userId = userId) == null)
            throw DocumentDeleteFailedException("No document found for delete physically.")

        collection.deleteOne(session, disabledFilter(userId = userId))
    }

    private val collection = getCollection<BaseProfileCollection>()
    private fun getDocument(session: ClientSession, userId: String) =
        collection.find(session, enabledFilter(userId = userId)).firstOrNull()
    private fun getDisabledDocument(session: ClientSession, userId: String) =
        collection.find(session, disabledFilter(userId = userId)).firstOrNull()
}
