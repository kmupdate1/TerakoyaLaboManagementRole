package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import jp.terakoyalabo.infrastructure.database.common.dto.BaseProfileCollection
import jp.terakoyalabo.infrastructure.database.common.dto.CoreInformationCollection
import jp.terakoyalabo.infrastructure.database.common.dto.ExtendedProfileCollection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class UserInfoInteraction(
    private val database: MongoDatabase,
) {
    fun createCoreInformation(collection: CoreInformationCollection): InsertOneResult? =
        database.getCollection<CoreInformationCollection>("core_information")
            .insertOne(collection)

    fun createBaseProfile(collection: BaseProfileCollection): InsertOneResult? =
        database.getCollection<BaseProfileCollection>("base_profile")
            .insertOne(collection)

    fun createExtendedProfile(collection: ExtendedProfileCollection): InsertOneResult? =
        database.getCollection<ExtendedProfileCollection>("extended_profile")
            .insertOne(collection)

    fun readCoreInformation(userId: String): CoreInformationCollection? =
        database.getCollection<CoreInformationCollection>("core_information")
            .findOne(CoreInformationCollection::userId eq userId)

    fun readBaeProfile(userId: String): BaseProfileCollection? =
        database.getCollection<BaseProfileCollection>("base_profile")
            .findOne(BaseProfileCollection::userId eq userId)

    fun readExtendedProfile(userId: String): ExtendedProfileCollection? =
        database.getCollection<ExtendedProfileCollection>("extended_profile")
            .findOne(ExtendedProfileCollection::userId eq userId)
}
