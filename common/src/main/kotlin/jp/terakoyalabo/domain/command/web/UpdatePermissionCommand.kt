package jp.terakoyalabo.domain.command.web

import jp.terakoyalabo.domain.value.permission.Description
import jp.terakoyalabo.domain.value.permission.DisplayName

data class UpdatePermissionCommand(
    val displayName: DisplayName,
    val description: Description? = null,
)
