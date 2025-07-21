package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.GraphQL
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureGraphQL() {
    install(GraphQL) {

    }
}
