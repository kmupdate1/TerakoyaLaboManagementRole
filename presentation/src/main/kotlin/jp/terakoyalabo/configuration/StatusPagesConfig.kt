package jp.terakoyalabo.configuration

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.respond
import jp.terakoyalabo.common.exception.domain.InvalidFormatException
import jp.terakoyalabo.common.exception.infrastructure.CollectionOperationFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentNotFoundException

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<InvalidFormatException> { call, cause ->
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
        exception<CollectionOperationFailedException> { call, cause ->
            call.also {
                it.application.log.error(cause.message)
            }.respond(
                status = HttpStatusCode.Unauthorized,
                message = mapOf(
                    "code" to "INVALID_INPUT_FORMAT",
                    "message" to cause.message,
                ),
            )
        }
        exception<DocumentNotFoundException> { call, cause ->
            call.also {
                it.application.log.error(cause.message)
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
