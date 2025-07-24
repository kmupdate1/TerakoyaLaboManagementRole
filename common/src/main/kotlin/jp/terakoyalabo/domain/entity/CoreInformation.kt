package jp.terakoyalabo.domain.entity

import jp.terakoyalabo.domain.value.core.Email

data class CoreInformation(
    val email: Email,
    val emailVerified: Boolean,
    val signInProvider: String,
)
