package jp.terakoyalabo.configuration

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            route("/v1") {
                route("/management-role") {
                    // graphQLPostRoute("/graphql")
                    post("/graphql") {

                        call.respondText { "Hello from Management Role API." }
                    }
                }
            }
        }
    }
}
