package jp.terakoyalabo.presentation.common

import io.ktor.http.HttpHeaders

val HttpHeaders.XApigatewayApiUserinfo: String
    get() = "X-Apigateway-Api-Userinfo"