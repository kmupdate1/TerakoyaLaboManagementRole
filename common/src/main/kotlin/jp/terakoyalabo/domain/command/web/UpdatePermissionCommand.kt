package jp.terakoyalabo.domain.command.web

import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.permission.Description
import jp.terakoyalabo.domain.value.permission.DisplayName
import jp.terakoyalabo.domain.value.permission.Name

data class UpdatePermissionCommand(
    val userId: UserId,
    val name: Name,
    val displayName: DisplayName,
    val description: Description? = null,
)
