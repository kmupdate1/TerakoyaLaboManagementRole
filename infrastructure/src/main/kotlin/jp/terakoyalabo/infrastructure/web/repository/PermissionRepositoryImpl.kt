package jp.terakoyalabo.infrastructure.web.repository

import jp.terakoyalabo.domain.command.web.CreatePermissionCommand
import jp.terakoyalabo.domain.command.web.UpdatePermissionCommand
import jp.terakoyalabo.domain.entity.web.Permission
import jp.terakoyalabo.domain.repository.web.PermissionRepository
import jp.terakoyalabo.domain.value.permission.Identifier
import jp.terakoyalabo.domain.value.permission.Name
import jp.terakoyalabo.infrastructure.web.interaction.PermissionInteraction

class PermissionRepositoryImpl(
    private val interaction: PermissionInteraction,
): PermissionRepository {
    override suspend fun create(command: CreatePermissionCommand): Permission? {
        TODO("Not yet implemented")
    }

    override suspend fun findByIdentifier(identifier: Identifier): Permission? {
        TODO("Not yet implemented")
    }

    override suspend fun findByName(name: Name): Permission? {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(): List<Permission> {
        TODO("Not yet implemented")
    }

    override suspend fun update(
        identifier: Identifier,
        command: UpdatePermissionCommand,
    ): Permission? {
        TODO("Not yet implemented")
    }

    override suspend fun softDelete(identifier: Identifier) {
        TODO("Not yet implemented")
    }

    override suspend fun hardDelete(identifier: Identifier) {
        TODO("Not yet implemented")
    }
}
