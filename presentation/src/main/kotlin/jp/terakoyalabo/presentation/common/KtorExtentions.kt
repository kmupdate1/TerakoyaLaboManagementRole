package jp.terakoyalabo.presentation.common

import io.ktor.http.HttpHeaders

val HttpHeaders.XAuthenticatedUserId: String
    get() = "X-Authenticated-User-Id"
val HttpHeaders.XAuthenticatedUserEmail: String
    get() = "X-Authenticated-User-Email"
