package jp.terakoyalabo.configuration

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jp.terakoyalabo.presentation.common.JwtPayload
import jp.terakoyalabo.presentation.common.XApigatewayApiUserinfo
import kotlinx.serialization.json.Json
import java.util.Base64

fun Application.configureRouting() {
    routing {
        route("/api") {
            route("/v1") {
                route("/management-role") {
                    // graphQLPostRoute("/graphql")
                    post("/graphql") {
                        val xApigatewayApiUserinfo = call.request.headers[HttpHeaders.XApigatewayApiUserinfo]
                            ?: run {
                                log.warn("${HttpHeaders.XApigatewayApiUserinfo} header not found.")
                                return@post call.respond(HttpStatusCode.Unauthorized)
                            }

                        call.request.headers.forEach { string, strings ->
                            log.info("$string: ${strings[0]}")
                        }

                        runCatching {
                            Base64.getUrlDecoder().decode(xApigatewayApiUserinfo)
                                .let { String(it, Charsets.UTF_8) }
                                .run {
                                    log.info("Decoded ${HttpHeaders.XApigatewayApiUserinfo} Payload: $this")
                                    Json.decodeFromString<JwtPayload>(this)
                                }
                        }.onSuccess { jwtPayload ->
                            log.info("SUCCESS decoding and parsing ${HttpHeaders.XApigatewayApiUserinfo}: user_id - ${jwtPayload.userId}, email - ${jwtPayload.email}")
                        }.onFailure { throwable ->
                            log.error("FAILED to decoding or parsing ${HttpHeaders.XApigatewayApiUserinfo}: ${throwable.message}", throwable)
                        }

                        call.respondText { "Hello from Management Role API." }
                    }
                }
            }
        }
    }
}
