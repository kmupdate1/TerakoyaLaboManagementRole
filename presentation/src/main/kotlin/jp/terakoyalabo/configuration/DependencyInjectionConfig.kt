package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import jp.terakoyalabo.infrastructure.database.interaction.SystemUtilityInteraction
import jp.terakoyalabo.infrastructure.database.interaction.UserInfoInteraction
import jp.terakoyalabo.infrastructure.database.repository.SystemUtilityRepositoryImpl
import jp.terakoyalabo.infrastructure.database.repository.UserInfoRepositoryImpl
import jp.terakoyalabo.application.resolver.mutation.SystemUtilityMutation
import jp.terakoyalabo.application.resolver.mutation.UserInfoMutation
import jp.terakoyalabo.application.resolver.query.UserInfoQuery
import jp.terakoyalabo.domain.repository.database.SystemUtilityRepository
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.presentation.factory.ManagementRoleContextFactory
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

private val monitoringModule = module {
}
private val contextFactoryModule = module {
    factory<KtorGraphQLContextFactory> { ManagementRoleContextFactory() }
}
private val databaseInteractionModule = module {
    factory { UserInfoInteraction(get()) }
    factory { SystemUtilityInteraction(get()) }
}
private val databaseRepositoryModule = module {
    factory<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
    factory<SystemUtilityRepository> { SystemUtilityRepositoryImpl(get()) }
}
private val queryResolverModule = module {
    factory { UserInfoQuery(get()) }
}
private val mutationResolverModule = module {
    factory { UserInfoMutation(get()) }
    factory { SystemUtilityMutation(get()) }
}

fun Application.configureDependencyInjection() {
    val mongoDatabase = configureDatabase()

    val dbCollectionModule = module {
        single<MongoDatabase> { mongoDatabase }
    }

    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(
            monitoringModule,
            dbCollectionModule,
            contextFactoryModule,
            databaseInteractionModule, databaseRepositoryModule,
            queryResolverModule, mutationResolverModule
        )
    }
}
