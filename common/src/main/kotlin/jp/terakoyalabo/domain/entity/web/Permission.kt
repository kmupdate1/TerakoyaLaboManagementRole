package jp.terakoyalabo.domain.entity.web

import jp.terakoyalabo.domain.value.permission.Description
import jp.terakoyalabo.domain.value.permission.DisplayName
import jp.terakoyalabo.domain.value.permission.Identifier
import jp.terakoyalabo.domain.value.permission.Name

data class Permission(
    val identifier: Identifier,
    val name: Name,
    val displayName: DisplayName,
    val description: Description,
)
