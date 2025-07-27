package jp.terakoyalabo.configuration

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.also {
                it.application.log.error(cause.message, cause)
            }.respond(
                status = HttpStatusCode.Unauthorized,
                message = mapOf(
                    "code" to "INVALID_INPUT_FORMAT",
                    "message" to cause.message,
                ),
            )
        }
    }
}
