package jp.terakoyalabo.presentation.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Identities(
    @SerialName("email") val email: List<String>,
)

@Serializable
data class Firebase(
    @SerialName("identities") val identities: Identities,
    @SerialName("sign_in_provider") val signInProvider: String,
)

@Serializable
data class JwtPayload(
    @SerialName("iss") val issuer: String,
    @SerialName("aud") val audience: String,
    @SerialName("auth_time") val authTime: Long,
    @SerialName("user_id") val userId: String,
    @SerialName("sub") val subject: String,
    @SerialName("iat") val iat: Long,
    @SerialName("exp") val expired: Long,
    @SerialName("email") val email: String,
    @SerialName("email_verified") val emailVerified: Boolean,
    @SerialName("firebase") val firebase: Firebase,
)
