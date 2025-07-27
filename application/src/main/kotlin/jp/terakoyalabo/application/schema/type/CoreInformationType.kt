package jp.terakoyalabo.application.schema.type

import jp.terakoyalabo.application.schema.common.Data

data class CoreInformationType(
    val userId: String,
    val email: String,
    val emailVerified: Boolean,
    val signInProvider: String,
): Data
