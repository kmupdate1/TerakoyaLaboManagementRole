package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
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
) {
    fun createExtendedProfile(collection: ExtendedProfileCollection): InsertOneResult? =
        database.getCollection<ExtendedProfileCollection>("extended_profile").also {
            it.findOne(
                and(
                    ExtendedProfileCollection::userId eq collection.userId,
                    ExtendedProfileCollection::disabled eq false,
                )
            ) ?: throw DocumentCreateFailedException("No document found for create.")
        }.insertOne(collection)

    fun referenceExtendedProfile(userId: String): ExtendedProfileCollection? =
        database.getCollection<ExtendedProfileCollection>("extended_profile")
            .findOne(
                and(
                    ExtendedProfileCollection::userId eq userId,
                    ExtendedProfileCollection::disabled eq false,
                )
            )

    fun updateExtendedProfile(collection: ExtendedProfileCollection): UpdateResult? =
        database.getCollection<ExtendedProfileCollection>("extended_profile").also {
            it.findOne(
                and(
                    ExtendedProfileCollection::userId eq collection.userId,
                    ExtendedProfileCollection::disabled eq false,
                )
            ) ?: throw DocumentUpdateFailedException("No document found for update.")
        }.updateOne(
            ExtendedProfileCollection::userId eq collection.userId,
            combine(
                set(SetTo(ExtendedProfileCollection::gender, collection.gender)),
                set(SetTo(ExtendedProfileCollection::dateOfBirth, collection.dateOfBirth)),
                set(SetTo(ExtendedProfileCollection::phoneNumber, collection.phoneNumber)),
                set(SetTo(ExtendedProfileCollection::postalCode, collection.postalCode)),
                set(SetTo(ExtendedProfileCollection::country, collection.country)),
                set(SetTo(ExtendedProfileCollection::address, collection.address)),
                set(SetTo(ExtendedProfileCollection::occupation, collection.occupation)),
                set(SetTo(ExtendedProfileCollection::selfIntroduction, collection.selfIntroduction)),
                set(SetTo(ExtendedProfileCollection::interests, collection.interests)),
                set(SetTo(ExtendedProfileCollection::updatedAt, collection.updatedAt)),
                set(SetTo(ExtendedProfileCollection::updatedBy, collection.userId)),
            ),
        )

    fun deleteLogicallyExtendedProfile(userId: String, updatedAt: Long): UpdateResult? =
        database.getCollection<ExtendedProfileCollection>("extended_profile").also {
            it.findOne(
                and(
                    ExtendedProfileCollection::userId eq userId,
                    ExtendedProfileCollection::disabled eq false,
                )
            ) ?: throw DocumentUpdateFailedException("No document found for delete logically.")
        }.updateOne(
            ExtendedProfileCollection::userId eq userId,
            combine(
                set(SetTo(ExtendedProfileCollection::disabled, true)),
                set(SetTo(ExtendedProfileCollection::updatedAt, updatedAt)),
                set(SetTo(ExtendedProfileCollection::updatedBy, userId)),
            ),
        )

    fun deletePhysicallyExtendedProfile(userId: String): DeleteResult? =
        database.getCollection<ExtendedProfileCollection>("extended_profile").also {
            it.findOne(
                and(
                    ExtendedProfileCollection::userId eq userId,
                    ExtendedProfileCollection::disabled eq true,
                )
            ) ?: throw DocumentDeleteFailedException("No document found for delete physically.")
        }.deleteOne(ExtendedProfileCollection::userId eq userId)
}
