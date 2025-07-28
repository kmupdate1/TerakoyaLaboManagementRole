package jp.terakoyalabo.infrastructure.database.repository

import jp.terakoyalabo.infrastructure.database.common.dto.SigningHistoryCollection
import jp.terakoyalabo.infrastructure.database.interaction.SystemUtilityInteraction
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.domain.entity.database.SigningHistory
import jp.terakoyalabo.domain.repository.database.SystemUtilityRepository
import jp.terakoyalabo.domain.value.core.UserId

class SystemUtilityRepositoryImpl(
    private val interaction: SystemUtilityInteraction,
): SystemUtilityRepository {
    override suspend fun recordSigningHistory(userId: UserId, entity: SigningHistory) {
        val now = System.currentTimeMillis()

        val collection = SigningHistoryCollection(
            userId = userId.toString(),
            authTime = entity.authTime,
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.recordSigningHistory(collection = collection)?.run {}
            ?: throw DocumentCreateFailedException("Failed to record signing history.")
    }
}
