package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Updates.set
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.infrastructure.database.common.dto.CoreInformationCollection
import org.litote.kmongo.*

class CoreInformationInteraction(
    private val database: MongoDatabase,
): BaseInteraction() {
    fun createCoreInformation(information: CoreInformationCollection): InsertOneResult? {
        val document = collection.findOne(enabledFilter(userId = information.userId))
        if (document != null)
            throw DocumentCreateFailedException("No document found for create.")

        return collection.insertOne(information)
    }

    fun referenceCoreInformation(userId: String): CoreInformationCollection? =
        collection.findOne(enabledFilter(userId = userId))

    fun updateCoreInformation(information: CoreInformationCollection): UpdateResult? = collection.also {
        it.findOne(enabledFilter(userId = information.userId))
            ?: throw DocumentUpdateFailedException("No document found for update.")
    }.updateOne(
        enabledFilter(userId = information.userId),
        combine(
            set("sign_in_provider", information.signInProvider),
            set("email_verified", information.emailVerified),
            set("updated_at", information.updatedAt),
            set("updated_by", information.updatedBy),
        ),
    )

    fun deleteLogicallyCoreInformation(userId: String, updatedAt: Long): UpdateResult? = collection.also {
        it.findOne(enabledFilter(userId = userId))
            ?: throw DocumentDeleteFailedException("No document found for delete logically.")
    }.updateOne(
        enabledFilter(userId = userId),
        combine(
            set("disabled", true),
            set("updated_at", updatedAt),
            set("updated_by", userId),
        ),
    )

    fun deletePhysicallyCoreInformation(userId: String): DeleteResult? = collection.also {
        it.findOne(disabledFilter(userId = userId))
            ?: throw DocumentDeleteFailedException("No document found for delete physically.")
    }.deleteOne(disabledFilter(userId = userId))

    private val collection get() = database.getCollection<CoreInformationCollection>("core_information")
}
