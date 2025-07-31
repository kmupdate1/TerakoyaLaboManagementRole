package jp.terakoyalabo.module

import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import io.ktor.server.application.*
import jp.terakoyalabo.application.resolver.mutation.BaseProfileMutation
import jp.terakoyalabo.application.resolver.mutation.CoreInformationMutation
import jp.terakoyalabo.application.resolver.mutation.SystemUtilityMutation
import jp.terakoyalabo.application.resolver.mutation.ExtendedProfileMutation
import jp.terakoyalabo.application.resolver.mutation.PermissionMutation
import jp.terakoyalabo.application.resolver.query.BaseProfileQuery
import jp.terakoyalabo.application.resolver.query.CoreInformationQuery
import jp.terakoyalabo.application.resolver.query.ExtendedProfileQuery
import jp.terakoyalabo.application.resolver.query.PermissionQuery
import org.koin.ktor.ext.inject

fun Application.configureGraphQL() {
    val schemaConfig = environment.config.config("graphql.schema")
    val serverConfig = environment.config.config("graphql.server")
    val engineConfig = environment.config.config("graphql.engine")

    val managementRoleContextFactory by inject<KtorGraphQLContextFactory>()

    val coreInformationQuery by inject<CoreInformationQuery>()
    val baseProfileQuery by inject<BaseProfileQuery>()
    val extendedProfileQuery by inject<ExtendedProfileQuery>()
    val permissionQuery by inject<PermissionQuery>()

    val coreInformationMutation by inject<CoreInformationMutation>()
    val baseProfileMutation by inject<BaseProfileMutation>()
    val extendedProfileMutation by inject<ExtendedProfileMutation>()
    val permissionMutation by inject<PermissionMutation>()
    val systemUtilityMutation by inject<SystemUtilityMutation>()

    install(GraphQL) {
        server {
            contextFactory = managementRoleContextFactory
        }
        schema {
            packages = schemaConfig.property("packages").getList()
            queries = listOf(
                coreInformationQuery,
                baseProfileQuery,
                extendedProfileQuery,
                permissionQuery,
            )
            mutations = listOf(
                coreInformationMutation,
                baseProfileMutation,
                extendedProfileMutation,
                permissionMutation,
                systemUtilityMutation
            )
        }
        engine {}
    }
}
