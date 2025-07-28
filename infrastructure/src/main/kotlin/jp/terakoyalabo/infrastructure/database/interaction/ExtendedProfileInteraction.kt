package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Updates.set
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.infrastructure.database.common.dto.ExtendedProfileCollection
import org.litote.kmongo.*

class ExtendedProfileInteraction(
    private val database: MongoDatabase,
): BaseInteraction() {
    fun createExtendedProfile(profile: ExtendedProfileCollection): InsertOneResult? {
        val document = collection.findOne(enabledFilter(userId = profile.userId))
        if (document != null)
            throw DocumentCreateFailedException("No document found for create.")

        return collection.insertOne(profile)
    }

    fun referenceExtendedProfile(userId: String): ExtendedProfileCollection? =
        collection.findOne(enabledFilter(userId = userId))

    fun updateExtendedProfile(profile: ExtendedProfileCollection): UpdateResult? = collection.also {
        it.findOne(enabledFilter(userId = profile.userId))
            ?: throw DocumentUpdateFailedException("No document found for update.")
    }.updateOne(
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

    fun deleteLogicallyExtendedProfile(userId: String, updatedAt: Long): UpdateResult? = collection.also {
        it.findOne(enabledFilter(userId = userId))
            ?: throw DocumentUpdateFailedException("No document found for delete logically.")
    }.updateOne(
        enabledFilter(userId = userId),
        combine(
            set("disabled", true),
            set("updated_at", updatedAt),
            set("updated_by", userId),
        ),
    )

    fun deletePhysicallyExtendedProfile(userId: String): DeleteResult? = collection.also {
        it.findOne(disabledFilter(userId = userId))
            ?: throw DocumentDeleteFailedException("No document found for delete physically.")
    }.deleteOne(disabledFilter(userId = userId))

    private val collection get() = database.getCollection<ExtendedProfileCollection>("extended_profile")
}
