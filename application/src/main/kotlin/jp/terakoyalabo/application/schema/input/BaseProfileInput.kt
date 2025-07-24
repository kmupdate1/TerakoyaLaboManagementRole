package jp.terakoyalabo.application.schema.input

data class BaseProfileInput(
    val firstName: String?,
    val familyName: String?,
    val firstNameKana: String?,
    val familyNameKana: String?,
    val displayName: String?,
)
