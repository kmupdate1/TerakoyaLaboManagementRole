package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.litote.kmongo.and
import org.litote.kmongo.getCollection

open class DatabaseInteraction(
    private val client: MongoClient,
    open val database: MongoDatabase,
    val collectionName: String,
) {
    protected inline fun <reified T: Any> getCollection(): MongoCollection<T> =
        database.getCollection<T>(collectionName)

    protected fun enabledFilter(userId: String) = and(Filters.eq("user_id", userId), Filters.eq("disabled", false))
    protected fun disabledFilter(userId: String) = and(Filters.eq("user_id", userId), Filters.eq("disabled", true))

    protected fun <R> transaction(block:(ClientSession) -> R): R = client.startSession().use { session ->
        session.startTransaction()
        try {
            val result = block(session)
            session.commitTransaction()
            result
        } catch (e: Exception) {
            session.abortTransaction()
            throw e
        }
    }
}
