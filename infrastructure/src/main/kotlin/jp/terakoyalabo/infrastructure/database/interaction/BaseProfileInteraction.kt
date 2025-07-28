package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Updates.set
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.infrastructure.database.common.dto.BaseProfileCollection
import org.litote.kmongo.*

class BaseProfileInteraction(
    private val database: MongoDatabase,
): BaseInteraction() {
    fun createBaseProfile(profile: BaseProfileCollection): InsertOneResult? {
        val document = collection.findOne(enabledFilter(userId = profile.userId))
        if (document != null)
            throw DocumentCreateFailedException("An active document with the specified information already exists. Cannot create a new document.")

        return collection.insertOne(profile)
    }

    fun referenceBaseProfile(userId: String): BaseProfileCollection? =
        collection.findOne(enabledFilter(userId = userId))

    fun updateBaseProfile(profile: BaseProfileCollection): UpdateResult? = collection.also {
        it.findOne(enabledFilter(userId = profile.userId))
            ?: throw DocumentUpdateFailedException("No document found for update.")
    }.updateOne(
        enabledFilter(userId = profile.userId),
        combine(
            set("first_name", profile.firstName),
            set("first_name_kana", profile.firstNameKana),
            set("family_name", profile.familyName),
            set("family_name_kana", profile.familyNameKana),
            set("display_name", profile.displayName),
            set("updated_at", profile.updatedAt),
            set("updated_by", profile.updatedBy),
        )
    )

    fun deleteLogicallyBaseProfile(userId: String, updatedAt: Long): UpdateResult? = collection.also {
        it.findOne(enabledFilter(userId = userId))
            ?: throw DocumentDeleteFailedException("No document found for delete logically.")
    }.updateOne(
        enabledFilter(userId = userId),
        combine(
            set("disabled", true),
            set("updated_at", updatedAt),
            set("updated_by", userId),
        )
    )

    fun deletePhysicallyBaseProfile(userId: String): DeleteResult? = collection.also {
        it.findOne(disabledFilter(userId = userId))
            ?: throw DocumentDeleteFailedException("No document found for delete physically.")
    }.deleteOne(disabledFilter(userId = userId))

    private val collection get() = database.getCollection<BaseProfileCollection>("base_profile")
}
