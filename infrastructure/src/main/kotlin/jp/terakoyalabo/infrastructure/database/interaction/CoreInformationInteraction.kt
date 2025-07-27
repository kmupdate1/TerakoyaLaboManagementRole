package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
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
) {
    fun createCoreInformation(collection: CoreInformationCollection): InsertOneResult? =
        database.getCollection<CoreInformationCollection>("core_information").also {
            it.findOne(
                and(
                    CoreInformationCollection::userId eq collection.userId,
                    CoreInformationCollection::disabled eq false,
                )
            ) ?: throw DocumentCreateFailedException("No document found for create.")
        }.insertOne(collection)
    
    fun referenceCoreInformation(userId: String): CoreInformationCollection? =
        database.getCollection<CoreInformationCollection>("core_information")
            .findOne(
                and(
                    CoreInformationCollection::userId eq userId,
                    CoreInformationCollection::disabled eq false,
                )
            )

    fun updateCoreInformation(collection: CoreInformationCollection): UpdateResult? =
        database.getCollection<CoreInformationCollection>("core_information").also {
            it.findOne(
                and(
                    CoreInformationCollection::userId eq collection.userId,
                    CoreInformationCollection::disabled eq false,
                )
            ) ?: throw DocumentUpdateFailedException("No document found for update.")
        }.updateOne(
            CoreInformationCollection::userId eq collection.userId,
            combine(
                set(SetTo(CoreInformationCollection::signInProvider, collection.signInProvider)),
                set(SetTo(CoreInformationCollection::emailVerified, collection.emailVerified)),
                set(SetTo(CoreInformationCollection::updatedAt, collection.updatedAt)),
                set(SetTo(CoreInformationCollection::updatedBy, collection.updatedBy)),
            ),
        )

    fun deleteLogicallyCoreInformation(userId: String, updatedAt: Long): UpdateResult? =
        database.getCollection<CoreInformationCollection>("core_information").also {
            it.findOne(
                and(
                    CoreInformationCollection::userId eq userId,
                    CoreInformationCollection::disabled eq false,
                )
            ) ?: throw DocumentDeleteFailedException("No document found for delete logically.")
        }.updateOne(
            CoreInformationCollection::userId eq userId,
            combine(
                set(SetTo(CoreInformationCollection::disabled, true)),
                set(SetTo(CoreInformationCollection::updatedAt, updatedAt)),
                set(SetTo(CoreInformationCollection::updatedBy, userId)),
            ),
        )

    fun deletePhysicallyCoreInformation(userId: String): DeleteResult? =
        database.getCollection<CoreInformationCollection>("core_information").also {
            it.findOne(
                and(
                    CoreInformationCollection::userId eq userId,
                    CoreInformationCollection::disabled eq true,
                )
            ) ?: throw DocumentDeleteFailedException("No document found for delete physically.")
        }.deleteOne(CoreInformationCollection::userId eq userId)
}
