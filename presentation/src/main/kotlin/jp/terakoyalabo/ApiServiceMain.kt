package jp.terakoyalabo

import io.ktor.server.application.*
import io.ktor.server.cio.*
import jp.terakoyalabo.configuration.*

fun Application.configuration() {
    configureDependencyInjection()
    configureApiGateway()
    configureGraphQL()
    configureRouting()
    configureStatusPages()
}

fun main(args: Array<String>) = EngineMain.main(args = args)
