package jp.terakoyalabo.domain.repository.database

import jp.terakoyalabo.domain.entity.CoreInformation
import jp.terakoyalabo.domain.value.core.UserId

interface CoreInformationRepository {
    suspend fun createCoreInformation(userId: UserId, entity: CoreInformation)
    suspend fun referenceCoreInformation(userId: UserId): CoreInformation
    suspend fun updateCoreInformation(userId: UserId, entity: CoreInformation)
    suspend fun deleteLogicallyCoreInformation(userId: UserId)
    suspend fun deletePhysicallyCoreInformation(userId: UserId)
}
