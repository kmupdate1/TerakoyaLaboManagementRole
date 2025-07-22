package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import io.ktor.server.application.*
import jp.terakoyalabo.application.resolver.query.UserQuery
import org.koin.ktor.ext.inject

fun Application.configureGraphQL() {
    val schemaConfig = environment.config.config("graphql.schema")
    val serverConfig = environment.config.config("graphql.server")
    val engineConfig = environment.config.config("graphql.engine")

    val managementRoleContextFactory by inject<KtorGraphQLContextFactory>()

    val userQuery by inject<UserQuery>()

    install(GraphQL) {
        schema {
            packages = schemaConfig.property("packages").getList()
            queries = listOf(
                userQuery,
            )
            mutations = listOf(

            )
        }
        server {
            contextFactory = managementRoleContextFactory
        }
        engine {

        }
    }
}
