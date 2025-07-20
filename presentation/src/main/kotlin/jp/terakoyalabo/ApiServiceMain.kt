package jp.terakoyalabo

import io.ktor.server.application.Application
import io.ktor.server.cio.EngineMain
import jp.terakoyalabo.configuration.configureRouting

fun Application.module() {
    configureRouting()
}

fun main(args: Array<String>) = EngineMain.main(args = args)
