package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import io.ktor.server.application.*
import jp.terakoyalabo.application.resolver.mutation.UserInfoMutation
import jp.terakoyalabo.application.resolver.query.UserInfoQuery
import org.koin.ktor.ext.inject

fun Application.configureGraphQL() {
    val schemaConfig = environment.config.config("graphql.schema")
    val serverConfig = environment.config.config("graphql.server")
    val engineConfig = environment.config.config("graphql.engine")

    val managementRoleContextFactory by inject<KtorGraphQLContextFactory>()

    val userInfoQuery by inject<UserInfoQuery>()
    val userInfoMutation by inject<UserInfoMutation>()

    install(GraphQL) {
        schema {
            packages = schemaConfig.property("packages").getList()
            queries = listOf(
                userInfoQuery,
            )
            mutations = listOf(
                userInfoMutation,
            )
        }
        server {
            contextFactory = managementRoleContextFactory
        }
        engine {

        }
    }
}
