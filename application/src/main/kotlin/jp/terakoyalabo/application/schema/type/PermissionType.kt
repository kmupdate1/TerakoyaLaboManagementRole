package jp.terakoyalabo.application.schema.type

import jp.terakoyalabo.application.schema.common.Data

data class PermissionType(
    val identifier: String,
    val name: String,
    val displayName: String,
    val description: String,
): Data
