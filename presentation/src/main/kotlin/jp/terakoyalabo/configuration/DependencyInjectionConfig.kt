package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import io.ktor.server.application.*
import jp.terakoyalabo.application.resolver.query.UserQuery
import jp.terakoyalabo.presentation.factory.ManagementRoleContextFactory
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

private val contextFactoryModule = module {
    factory<KtorGraphQLContextFactory> { ManagementRoleContextFactory() }
}
private val queryResolverModule = module {
    factory { UserQuery() }
}
private val mutationResolverModule = module {

}

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(
            contextFactoryModule,
            queryResolverModule, mutationResolverModule
        )
    }
}
