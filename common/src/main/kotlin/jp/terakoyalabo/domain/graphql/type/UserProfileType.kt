package jp.terakoyalabo.domain.graphql.type

data class UserProfileType(
    val userId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val status: String,
    val lastLogin: Int,
)
