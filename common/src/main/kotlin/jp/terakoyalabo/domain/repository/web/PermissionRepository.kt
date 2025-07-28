package jp.terakoyalabo.domain.repository.web

import jp.terakoyalabo.domain.command.web.CreatePermissionCommand
import jp.terakoyalabo.domain.command.web.UpdatePermissionCommand
import jp.terakoyalabo.domain.entity.web.Permission
import jp.terakoyalabo.domain.value.permission.Identifier
import jp.terakoyalabo.domain.value.permission.Name

interface PermissionRepository {
    suspend fun create(command: CreatePermissionCommand): Permission?
    suspend fun findByIdentifier(identifier: Identifier): Permission?
    suspend fun findByName(name: Name): Permission?
    suspend fun findAll(): List<Permission>
    suspend fun update(identifier: Identifier, command: UpdatePermissionCommand): Permission?
    suspend fun softDelete(identifier: Identifier)
    suspend fun hardDelete(identifier: Identifier)
}
