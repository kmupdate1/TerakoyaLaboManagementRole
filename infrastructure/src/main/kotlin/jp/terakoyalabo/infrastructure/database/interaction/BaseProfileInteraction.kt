package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
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
) {
    fun createBaseProfile(collection: BaseProfileCollection): InsertOneResult? =
        database.getCollection<BaseProfileCollection>("base_profile").also {
            it.findOne(
                and(
                    BaseProfileCollection::userId eq collection.userId,
                    BaseProfileCollection::disabled eq false,
                )
            ) ?: throw DocumentCreateFailedException("No document found for create.")
        }.insertOne(collection)

    fun referenceBaseProfile(userId: String): BaseProfileCollection? =
        database.getCollection<BaseProfileCollection>("base_profile")
            .findOne(
                and(
                    BaseProfileCollection::userId eq userId,
                    BaseProfileCollection::disabled eq false,
                )
            )

    fun updateBaseProfile(collection: BaseProfileCollection): UpdateResult? =
        database.getCollection<BaseProfileCollection>("base_profile").also {
            it.findOne(
                and(
                    BaseProfileCollection::userId eq collection.userId,
                    BaseProfileCollection::disabled eq false,
                )
            ) ?: throw DocumentUpdateFailedException("No document found for update.")
        }.updateOne(
            BaseProfileCollection::userId eq collection.userId,
            combine(
                set(SetTo(BaseProfileCollection::firstName, collection.firstName)),
                set(SetTo(BaseProfileCollection::firstNameKana, collection.firstNameKana)),
                set(SetTo(BaseProfileCollection::familyName, collection.familyName)),
                set(SetTo(BaseProfileCollection::familyNameKana, collection.familyNameKana)),
                set(SetTo(BaseProfileCollection::displayName, collection.displayName)),
                set(SetTo(BaseProfileCollection::updatedAt, collection.updatedAt)),
                set(SetTo(BaseProfileCollection::updatedBy, collection.updatedBy)),
            )
        )

    fun deleteLogicallyBaseProfile(userId: String, updatedAt: Long): UpdateResult? =
        database.getCollection<BaseProfileCollection>("base_profile").also {
            it.findOne(
                and(
                    BaseProfileCollection::userId eq userId,
                    BaseProfileCollection::disabled eq false,
                )
            ) ?: throw DocumentDeleteFailedException("No document found for delete logically.")
        }.updateOne(
            BaseProfileCollection::userId eq userId,
            combine(
                set(SetTo(BaseProfileCollection::disabled, true)),
                set(SetTo(BaseProfileCollection::updatedAt, updatedAt)),
                set(SetTo(BaseProfileCollection::updatedBy, userId)),
            )
        )

    fun deletePhysicallyBaseProfile(userId: String): DeleteResult? =
        database.getCollection<BaseProfileCollection>("base_profile").also {
            it.findOne(
                and(
                    BaseProfileCollection::userId eq userId,
                    BaseProfileCollection::disabled eq true,
                )
            ) ?: throw DocumentDeleteFailedException("No document found for delete physically.")
        }.deleteOne(BaseProfileCollection::userId eq userId)
}
