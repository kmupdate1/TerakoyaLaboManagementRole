package jp.terakoyalabo.domain.repository.database

import jp.terakoyalabo.domain.entity.database.BaseProfile
import jp.terakoyalabo.domain.value.core.UserId

interface BaseProfileRepository {
    suspend fun createBaseProfile(userId: UserId, entity: BaseProfile)
    suspend fun referenceBaseProfile(userId: UserId): BaseProfile
    suspend fun updateBaseProfile(userId: UserId, entity: BaseProfile)
    suspend fun deleteLogicallyBaseProfile(userId: UserId)
    suspend fun deletePhysicallyBaseProfile(userId: UserId)
}
