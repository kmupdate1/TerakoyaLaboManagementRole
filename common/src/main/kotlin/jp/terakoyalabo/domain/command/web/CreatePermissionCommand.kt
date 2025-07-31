package jp.terakoyalabo.domain.command.web

import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.permission.Description
import jp.terakoyalabo.domain.value.permission.DisplayName
import jp.terakoyalabo.domain.value.permission.Identifier
import jp.terakoyalabo.domain.value.permission.Name

data class CreatePermissionCommand(
    val userId: UserId,
    val identifier: Identifier,
    val name: Name,
    val displayName: DisplayName,
    val description: Description? = null,
)
