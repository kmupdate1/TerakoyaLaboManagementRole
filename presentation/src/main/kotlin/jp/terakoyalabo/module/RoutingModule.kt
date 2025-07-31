package jp.terakoyalabo.module

import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            route("/v1") {
                route("/management-role") {
                    graphQLPostRoute("/graphql")
                }
            }
        }
    }
}
