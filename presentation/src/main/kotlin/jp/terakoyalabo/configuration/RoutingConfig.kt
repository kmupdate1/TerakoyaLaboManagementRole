package jp.terakoyalabo.configuration

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jp.terakoyalabo.presentation.common.XAuthenticatedUserEmail
import jp.terakoyalabo.presentation.common.XAuthenticatedUserId

fun Application.configureRouting() {
    routing {
        route("/api") {
            route("/v1") {
                route("/management-role") {
                    // graphQLPostRoute("/graphql")
                    post("/graphql") {
                        val xAuthenticatedUserId = call.request.headers[HttpHeaders.XAuthenticatedUserId]
                        val xAuthenticatedUserEmail = call.request.headers[HttpHeaders.XAuthenticatedUserEmail]

                        call.request.headers.forEach { string, strings ->
                            log.info("$string: ${strings[0]}")
                        }

                        call.respondText { "Hello from Management Role API." }
                    }
                }
            }
        }
    }
}
