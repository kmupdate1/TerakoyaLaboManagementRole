package jp.terakoyalabo.domain.repository.database

import jp.terakoyalabo.domain.entity.database.ExtendedProfile
import jp.terakoyalabo.domain.value.core.UserId

interface ExtendedProfileRepository {
    suspend fun createExtendedProfile(userId: UserId, entity: ExtendedProfile)
    suspend fun referenceExtendedProfile(userId: UserId): ExtendedProfile
    suspend fun updateExtendedProfile(userId: UserId, entity: ExtendedProfile)
    suspend fun deleteLogicallyExtendedProfile(userId: UserId)
    suspend fun deletePhysicallyExtendedProfile(userId: UserId)
}
