package jp.terakoyalabo.domain.repository.database

import jp.terakoyalabo.domain.entity.BaseProfile
import jp.terakoyalabo.domain.entity.CoreInformation
import jp.terakoyalabo.domain.entity.ExtendedProfile
import jp.terakoyalabo.domain.value.core.UserId

interface UserInfoRepository {
    suspend fun createCoreInformation(userId: UserId, entity: CoreInformation)
    suspend fun createBaseProfile(userId: UserId, entity: BaseProfile)
    suspend fun createExtendedProfile(userId: UserId, entity: ExtendedProfile)
    suspend fun readCoreInformation(userId: UserId): CoreInformation
    suspend fun readBaseProfile(userId: UserId): BaseProfile
    suspend fun readExtendedProfile(userId: UserId): ExtendedProfile
}
