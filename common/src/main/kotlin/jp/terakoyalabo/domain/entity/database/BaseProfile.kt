package jp.terakoyalabo.domain.entity.database

import jp.terakoyalabo.domain.value.base.*

data class BaseProfile(
    val firstName: FirstName,
    val familyName: FamilyName,
    val firstNameKana: FirstNameKana,
    val familyNameKana: FamilyNameKana,
    val displayName: DisplayName,
)
