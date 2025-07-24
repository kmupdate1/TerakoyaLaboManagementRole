package jp.terakoyalabo.application.schema.input

data class ExtendedProfileInput(
    val gender: String?,
    val dateOfBirth: String?,
    val phoneNumber: String?,
    val postalCode: String?,
    val country: String?,
    val address: String?,
    val occupation: String?,
    val selfIntroduction: String?,
    val interests: String?,
)
