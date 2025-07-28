package jp.terakoyalabo.configuration

import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import jp.terakoyalabo.application.resolver.mutation.BaseProfileMutation
import jp.terakoyalabo.application.resolver.mutation.CoreInformationMutation
import jp.terakoyalabo.application.resolver.mutation.ExtendedProfileMutation
import jp.terakoyalabo.application.resolver.mutation.SystemUtilityMutation
import jp.terakoyalabo.application.resolver.query.BaseProfileQuery
import jp.terakoyalabo.application.resolver.query.CoreInformationQuery
import jp.terakoyalabo.application.resolver.query.ExtendedProfileQuery
import jp.terakoyalabo.domain.repository.database.BaseProfileRepository
import jp.terakoyalabo.domain.repository.database.CoreInformationRepository
import jp.terakoyalabo.domain.repository.database.ExtendedProfileRepository
import jp.terakoyalabo.domain.repository.database.SystemUtilityRepository
import jp.terakoyalabo.domain.repository.web.PermissionRepository
import jp.terakoyalabo.infrastructure.database.interaction.BaseProfileInteraction
import jp.terakoyalabo.infrastructure.database.interaction.CoreInformationInteraction
import jp.terakoyalabo.infrastructure.database.interaction.ExtendedProfileInteraction
import jp.terakoyalabo.infrastructure.database.interaction.SystemUtilityInteraction
import jp.terakoyalabo.infrastructure.database.repository.BaseProfileRepositoryImpl
import jp.terakoyalabo.infrastructure.database.repository.CoreInformationRepositoryImpl
import jp.terakoyalabo.infrastructure.database.repository.ExtendedProfileRepositoryImpl
import jp.terakoyalabo.infrastructure.database.repository.SystemUtilityRepositoryImpl
import jp.terakoyalabo.infrastructure.web.interaction.PermissionInteraction
import jp.terakoyalabo.infrastructure.web.repository.PermissionRepositoryImpl
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
    factory { CoreInformationInteraction(get()) }
    factory { BaseProfileInteraction(get()) }
    factory { ExtendedProfileInteraction(get()) }
    factory { SystemUtilityInteraction(get()) }
}
private val webInteractionModule = module {
    factory { PermissionInteraction() }
}
private val databaseRepositoryModule = module {
    factory<CoreInformationRepository> { CoreInformationRepositoryImpl(get()) }
    factory<BaseProfileRepository> { BaseProfileRepositoryImpl(get()) }
    factory<ExtendedProfileRepository> { ExtendedProfileRepositoryImpl(get()) }
    factory<SystemUtilityRepository> { SystemUtilityRepositoryImpl(get()) }
}
private val webRepositoryModule = module {
    factory<PermissionRepository> { PermissionRepositoryImpl(get()) }
}
private val queryResolverModule = module {
    factory { CoreInformationQuery(get()) }
    factory { BaseProfileQuery(get()) }
    factory { ExtendedProfileQuery(get()) }
}
private val mutationResolverModule = module {
    factory { CoreInformationMutation(get()) }
    factory { BaseProfileMutation(get()) }
    factory { ExtendedProfileMutation(get()) }
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
            webInteractionModule, webRepositoryModule,
            queryResolverModule, mutationResolverModule
        )
    }
}
