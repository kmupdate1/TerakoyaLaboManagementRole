package jp.terakoyalabo.infrastructure.web.repository

import jp.terakoyalabo.common.exception.infrastructure.CollectionOperationFailedException
import jp.terakoyalabo.domain.command.web.CreatePermissionCommand
import jp.terakoyalabo.domain.command.web.UpdatePermissionCommand
import jp.terakoyalabo.domain.entity.web.Permission
import jp.terakoyalabo.domain.repository.web.PermissionRepository
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.permission.Description
import jp.terakoyalabo.domain.value.permission.DisplayName
import jp.terakoyalabo.domain.value.permission.Identifier
import jp.terakoyalabo.domain.value.permission.Name
import jp.terakoyalabo.infrastructure.web.common.dto.PermissionsDto
import jp.terakoyalabo.infrastructure.web.interaction.PermissionInteraction

class PermissionRepositoryImpl(
    private val interaction: PermissionInteraction,
): PermissionRepository {
    override suspend fun create(command: CreatePermissionCommand) {
        val now = System.currentTimeMillis()

        val document = PermissionsDto(
            identifier = command.identifier.toString(),
            name = command.name.toString(),
            displayName = command.displayName.toString(),
            description = command.description?.toString(),
            disabled = false,
            createdAt = now,
            updatedAt = now,
            createdBy = command.userId.toString(),
            updatedBy = command.userId.toString(),
        )

        return interaction.create(document = document).get()?.run {}
            ?: throw CollectionOperationFailedException("Failed to create permission.")
    }

    override suspend fun findByIdentifier(identifier: Identifier): Permission? =
        interaction.findByIdentifier(identifier = identifier.toString()).get()?.map {
            val identifier = Identifier.init(it.getString("identifier")).getOrThrow()
            val name = Name.init(it.getString("name")).getOrThrow()
            val displayName = DisplayName.init(it.getString("display_name")).getOrThrow()
            val description = Description.init(it.getString("description")).getOrThrow()

            Permission(
                identifier = identifier,
                name = name,
                displayName = displayName,
                description = description,
            )
        }?.single()
            ?: throw CollectionOperationFailedException("Failed to find permission by identifier.")

    override suspend fun findByName(name: Name): Permission? =
        interaction.findByName(name = name.toString()).get()?.map {
            val identifier = Identifier.init(it.getString("identifier")).getOrThrow()
            val name = Name.init(it.getString("name")).getOrThrow()
            val displayName = DisplayName.init(it.getString("display_name")).getOrThrow()
            val description = Description.init(it.getString("description")).getOrThrow()

            Permission(
                identifier = identifier,
                name = name,
                displayName = displayName,
                description = description,
            )
        }?.single()
            ?: throw CollectionOperationFailedException("Failed to find permission by name.")

    override suspend fun findAll(): List<Permission> = interaction.findAll()?.map {
        val identifier = Identifier.init(it.getString("identifier")).getOrThrow()
        val name = Name.init(it.getString("name")).getOrThrow()
        val displayName = DisplayName.init(it.getString("displayName")).getOrThrow()
        val description = Description.init(it.getString("description")).getOrThrow()

        Permission(
            identifier = identifier,
            name = name,
            displayName = displayName,
            description = description,
        )
    } ?: throw CollectionOperationFailedException("Failed to find all operation.")

    override suspend fun update(
        identifier: Identifier,
        command: UpdatePermissionCommand,
    ) {
        val now = System.currentTimeMillis()

        val document = PermissionsDto(
            identifier = identifier.toString(),
            name = command.name.toString(),
            displayName = command.displayName.toString(),
            description = command.description.toString(),
            createdAt = now,
            updatedAt = now,
            createdBy = command.userId.toString(),
            updatedBy = command.userId.toString(),
        )

        return interaction.update(document = document).get()?.run {}
            ?: throw CollectionOperationFailedException("Failed to update permission.")
    }

    override suspend fun softDelete(identifier: Identifier, userId: UserId) = interaction.softDelete(
        identifier = identifier.toString(),
        deletedAt = System.currentTimeMillis(),
        userId = userId.toString(),
    ).get()?.run {}
        ?: throw CollectionOperationFailedException("Failed to delete logically permissions.")

    override suspend fun hardDelete(identifier: Identifier, userId: UserId) =
        interaction.hardDelete(identifier = identifier.toString()).get()?.run {}
            ?: throw CollectionOperationFailedException("Failed to delete physically permission.")
}
