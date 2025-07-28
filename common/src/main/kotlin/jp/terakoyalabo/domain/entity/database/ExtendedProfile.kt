package jp.terakoyalabo.domain.entity.database

import jp.terakoyalabo.domain.value.extended.*

data class ExtendedProfile(
    val gender: Gender,
    val dateOfBirth: DateOfBirth,
    val phoneNumber: PhoneNumber,
    val postalCode: PostalCode,
    val country: Country,
    val address: Address,
    val occupation: Occupation,
    val selfIntroduction: SelfIntroduction,
    val interests: List<Interest>,
)
