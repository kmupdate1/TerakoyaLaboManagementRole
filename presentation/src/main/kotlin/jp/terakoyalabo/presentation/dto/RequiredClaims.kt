package jp.terakoyalabo.presentation.dto

import jp.lax256.apigateway.core.dto.Claims
import jp.lax256.apigateway.core.dto.Firebase
import jp.terakoyalabo.domain.value.Email

data class RequiredClaims(
    val userId: String,
    val email: Email,
    val emailVerified: Boolean,
    val authTime: Long,
    val firebase: Firebase,
): Claims
