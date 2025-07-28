package jp.terakoyalabo.infrastructure.database.repository

import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentNotFoundException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.domain.entity.CoreInformation
import jp.terakoyalabo.domain.repository.database.CoreInformationRepository
import jp.terakoyalabo.domain.value.core.Email
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.infrastructure.database.common.dto.CoreInformationCollection
import jp.terakoyalabo.infrastructure.database.interaction.CoreInformationInteraction

class CoreInformationRepositoryImpl(
    private val interaction: CoreInformationInteraction,
) : CoreInformationRepository {
    override suspend fun createCoreInformation(
        userId: UserId,
        entity: CoreInformation,
    ) {
        val now = System.currentTimeMillis()

        val collection = CoreInformationCollection(
            userId = userId.toString(),
            email = entity.email.toString(),
            emailVerified = entity.emailVerified,
            signInProvider = entity.signInProvider,
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.createCoreInformation(information = collection)?.run {}
            ?: throw DocumentCreateFailedException("Failed to create user core information.")
    }

    override suspend fun referenceCoreInformation(userId: UserId): CoreInformation =
        interaction.referenceCoreInformation(userId = userId.toString())?.let {
            val email = Email.init(it.email).getOrThrow()

            CoreInformation(
                email = email,
                emailVerified = it.emailVerified,
                signInProvider = it.signInProvider,
            )
        } ?: throw DocumentNotFoundException("No user core information found.")

    override suspend fun updateCoreInformation(
        userId: UserId,
        entity: CoreInformation,
    ) {
        val now = System.currentTimeMillis()

        val collection = CoreInformationCollection(
            userId = userId.toString(),
            email = entity.email.toString(),
            emailVerified = entity.emailVerified,
            signInProvider = entity.signInProvider,
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.updateCoreInformation(information = collection)?.run {}
            ?: throw DocumentUpdateFailedException("Failed to update user core information.")
    }

    override suspend fun deleteLogicallyCoreInformation(userId: UserId) {
        val now = System.currentTimeMillis()

        return interaction.deleteLogicallyCoreInformation(userId = userId.toString(), updatedAt = now)?.run {}
            ?: throw DocumentDeleteFailedException("Failed to delete logically user core information.")
    }

    override suspend fun deletePhysicallyCoreInformation(userId: UserId) =
        interaction.deletePhysicallyCoreInformation(userId = userId.toString())?.run {}
            ?: throw DocumentDeleteFailedException("Failed to delete physically user core information.")
}
