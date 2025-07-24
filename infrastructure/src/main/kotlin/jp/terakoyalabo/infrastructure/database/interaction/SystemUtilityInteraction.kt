package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.InsertOneResult
import jp.terakoyalabo.infrastructure.database.common.dto.SigningHistoryCollection
import org.litote.kmongo.getCollection

class SystemUtilityInteraction(
    private val database: MongoDatabase,
) {
    fun recordSigningHistory(collection: SigningHistoryCollection): InsertOneResult? =
        database.getCollection<SigningHistoryCollection>("signing_history")
            .insertOne(collection)
}
