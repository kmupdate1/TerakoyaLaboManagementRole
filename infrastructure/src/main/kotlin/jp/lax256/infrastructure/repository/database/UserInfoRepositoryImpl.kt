package jp.lax256.infrastructure.repository.database

import com.mongodb.client.MongoCollection
import jp.terakoyalabo.domain.entity.UserProfile
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.domain.value.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class UserInfoRepositoryImpl(
    private val database: MongoCollection<UserProfile>,
): UserInfoRepository {
    override suspend fun createProfile(entity: UserProfile): ObjectId =
        database.insertOne(entity).insertedId.run { ObjectId.init(this) }

    override suspend fun readProfile(userId: String): UserProfile? =
        database.findOne(UserProfile::userId eq userId)
}
