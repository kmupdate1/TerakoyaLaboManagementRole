package jp.terakoyalabo.application.schema.type

import jp.terakoyalabo.application.schema.common.Data

data class BaseProfileType(
    val userId: String,
    val firstName: String,
    val familyName: String,
    val firstNameKana: String,
    val familyNameKana: String,
    val displayName: String,
): Data
