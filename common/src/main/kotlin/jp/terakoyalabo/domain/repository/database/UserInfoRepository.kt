package jp.terakoyalabo.domain.repository.database

import jp.terakoyalabo.domain.entity.UserProfile
import jp.terakoyalabo.domain.value.ObjectId

interface UserInfoRepository {
    suspend fun createProfile(entity: UserProfile): ObjectId
    suspend fun readProfile(userId: String): UserProfile?
}
