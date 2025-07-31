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
import jp.terakoyalabo.infrastructure.database.common.dto.ExtendedProfileCollection
import org.litote.kmongo.combine

class ExtendedProfileInteraction(
    override val database: MongoDatabase,
    client: MongoClient,
): DatabaseInteraction(
    client = client,
    database = database,
    collectionName = "extended_profile",
) {
    fun createExtendedProfile(profile: ExtendedProfileCollection): InsertOneResult? = transaction { session ->
        if (getDocument(session = session, userId = profile.userId) != null)
            throw DocumentCreateFailedException("No document found for create.")

        collection.insertOne(session, profile)
    }

    fun referenceExtendedProfile(userId: String): ExtendedProfileCollection? = transaction { session ->
        getDocument(session = session, userId = userId)
    }

    fun updateExtendedProfile(profile: ExtendedProfileCollection): UpdateResult? = transaction { session ->
        if (getDocument(session = session, userId = profile.userId) == null)
            throw DocumentUpdateFailedException("No document found for update.")

        collection.updateOne(
            session,
            enabledFilter(userId = profile.userId),
            combine(
                set("gender", profile.gender),
                set("date_of_birth", profile.dateOfBirth),
                set("phone_number", profile.phoneNumber),
                set("postal_code", profile.postalCode),
                set("country", profile.country),
                set("address", profile.address),
                set("occupation", profile.occupation),
                set("self_introduction", profile.selfIntroduction),
                set("interests", profile.interests),
                set("updated_at", profile.updatedAt),
                set("updated_by", profile.userId),
            ),
        )
    }

    fun deleteLogicallyExtendedProfile(userId: String, updatedAt: Long): UpdateResult? = transaction { session ->
        if (getDocument(session = session, userId = userId) == null)
            throw DocumentUpdateFailedException("No document found for delete logically.")

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

    fun deletePhysicallyExtendedProfile(userId: String): DeleteResult? = transaction { session ->
        if (getDisabledDocument(session = session, userId = userId) == null)
            throw DocumentDeleteFailedException("No document found for delete physically.")

        collection.deleteOne(session, disabledFilter(userId = userId))
    }

    private val collection = getCollection<ExtendedProfileCollection>()
    private fun getDocument(session: ClientSession, userId: String) =
        collection.find(session, enabledFilter(userId = userId)).firstOrNull()
    private fun getDisabledDocument(session: ClientSession, userId: String) =
        collection.find(session, disabledFilter(userId = userId)).firstOrNull()
}
