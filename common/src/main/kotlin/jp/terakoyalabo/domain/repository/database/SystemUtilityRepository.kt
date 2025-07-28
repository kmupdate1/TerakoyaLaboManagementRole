package jp.terakoyalabo.domain.repository.database

import jp.terakoyalabo.domain.entity.database.SigningHistory
import jp.terakoyalabo.domain.value.core.UserId

interface SystemUtilityRepository {
    suspend fun recordSigningHistory(userId: UserId, entity: SigningHistory)
}
