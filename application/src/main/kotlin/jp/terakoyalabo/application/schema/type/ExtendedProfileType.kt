package jp.terakoyalabo.application.schema.type

import jp.terakoyalabo.application.schema.common.Data

data class ExtendedProfileType(
    val userId: String,
    val gender: String,
    val dateOfBirth: String,
    val phoneNumber: String,
    val postalCode: String,
    val country: String,
    val address: String,
    val occupation: String,
    val selfIntroduction: String,
    val interests: List<String>,
): Data
