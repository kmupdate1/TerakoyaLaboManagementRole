package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import io.ktor.server.application.*
import jp.lax256.infrastructure.repository.database.UserInfoRepositoryImpl
import jp.terakoyalabo.application.resolver.mutation.UserMutation
import jp.terakoyalabo.application.resolver.query.UserQuery
import jp.terakoyalabo.domain.entity.UserProfile
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.presentation.factory.ManagementRoleContextFactory
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.litote.kmongo.getCollection

private val monitoringModule = module {
}
private val contextFactoryModule = module {
    factory<KtorGraphQLContextFactory> { ManagementRoleContextFactory() }
}
private val databaseRepositoryModule = module {
    factory<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
}
private val queryResolverModule = module {
    factory { UserQuery(get()) }
}
private val mutationResolverModule = module {
    factory { UserMutation(get()) }
}

fun Application.configureDependencyInjection() {
    val mongoDatabase = configureDatabase()

    val dbCollectionModule = module {
        single { mongoDatabase.getCollection<UserProfile>("UserInfo") }
    }

    install(Koin) {
        slf4jLogger()
        modules(
            monitoringModule,
            dbCollectionModule,
            databaseRepositoryModule,
            contextFactoryModule,
            queryResolverModule, mutationResolverModule
        )
    }
}
