package jp.terakoyalabo.infrastructure.database.repository

import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentNotFoundException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.domain.entity.BaseProfile
import jp.terakoyalabo.domain.repository.database.BaseProfileRepository
import jp.terakoyalabo.domain.value.base.DisplayName
import jp.terakoyalabo.domain.value.base.FamilyName
import jp.terakoyalabo.domain.value.base.FamilyNameKana
import jp.terakoyalabo.domain.value.base.FirstName
import jp.terakoyalabo.domain.value.base.FirstNameKana
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.infrastructure.database.common.dto.BaseProfileCollection
import jp.terakoyalabo.infrastructure.database.interaction.BaseProfileInteraction

class BaseProfileRepositoryImpl(
    private val interaction: BaseProfileInteraction,
): BaseProfileRepository {
    override suspend fun createBaseProfile(
        userId: UserId,
        entity: BaseProfile,
    ) {
        val now = System.currentTimeMillis()

        val profile = BaseProfileCollection(
            userId = userId.toString(),
            firstName = entity.firstName.toString(),
            familyName = entity.familyName.toString(),
            firstNameKana = entity.firstNameKana.toString(),
            familyNameKana = entity.familyNameKana.toString(),
            displayName = entity.displayName.toString(),
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.createBaseProfile(profile = profile)?.run {}
            ?: throw DocumentNotFoundException("Failed to create base profile.")
    }

    override suspend fun referenceBaseProfile(userId: UserId): BaseProfile =
        interaction.referenceBaseProfile(userId = userId.toString())?.let {
            val firstName = FirstName.init(it.firstName).getOrThrow()
            val familyName = FamilyName.init(it.familyName).getOrThrow()
            val firstNameKana = FirstNameKana.init(it.firstNameKana).getOrThrow()
            val familyNameKana = FamilyNameKana.init(it.familyNameKana).getOrThrow()
            val displayName = DisplayName.init(it.displayName).getOrThrow()

            BaseProfile(
                firstName = firstName,
                familyName = familyName,
                firstNameKana = firstNameKana,
                familyNameKana = familyNameKana,
                displayName = displayName,
            )
        } ?: throw DocumentNotFoundException("No base profile found.")

    override suspend fun updateBaseProfile(
        userId: UserId,
        entity: BaseProfile,
    ) {
        val now = System.currentTimeMillis()

        val profile = BaseProfileCollection(
            userId = userId.toString(),
            firstName = entity.firstName.toString(),
            familyName = entity.familyName.toString(),
            firstNameKana = entity.firstNameKana.toString(),
            familyNameKana = entity.familyNameKana.toString(),
            displayName = entity.displayName.toString(),
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.updateBaseProfile(profile = profile)?.run {}
            ?: throw DocumentUpdateFailedException("Failed to update base profile.")
    }

    override suspend fun deleteLogicallyBaseProfile(userId: UserId) {
        val now = System.currentTimeMillis()

        interaction.deleteLogicallyBaseProfile(userId = userId.toString(), updatedAt = now)?.run {}
            ?: throw DocumentDeleteFailedException("Failed to delete logically base profile.")
    }

    override suspend fun deletePhysicallyBaseProfile(userId: UserId) =
        interaction.deletePhysicallyBaseProfile(userId = userId.toString())?.run {}
            ?: throw DocumentDeleteFailedException("Failed to delete physically base profile.")
}
