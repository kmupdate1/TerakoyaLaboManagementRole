package jp.terakoyalabo.application.schema.type

data class BaseProfileType(
    val firstName: String,
    val familyName: String,
    val firstNameKana: String,
    val familyNameKana: String,
    val displayName: String,
)
