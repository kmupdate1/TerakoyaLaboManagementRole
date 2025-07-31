package jp.terakoyalabo.module

import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import jp.terakoyalabo.application.resolver.mutation.*
import jp.terakoyalabo.application.resolver.query.BaseProfileQuery
import jp.terakoyalabo.application.resolver.query.CoreInformationQuery
import jp.terakoyalabo.application.resolver.query.ExtendedProfileQuery
import jp.terakoyalabo.application.resolver.query.PermissionQuery
import jp.terakoyalabo.configuration.databaseSettings
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
private val externalServiceModule = module {
    single<Firestore> { FirestoreClient.getFirestore() }
}
private val databaseInteractionModule = module {
    factory { CoreInformationInteraction(get(), get()) }
    factory { BaseProfileInteraction(get(), get()) }
    factory { ExtendedProfileInteraction(get(), get()) }
    factory { SystemUtilityInteraction(get()) }
}
private val webInteractionModule = module {
    factory { PermissionInteraction(get()) }
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
    factory { PermissionQuery(get()) }
}
private val mutationResolverModule = module {
    factory { CoreInformationMutation(get()) }
    factory { BaseProfileMutation(get()) }
    factory { ExtendedProfileMutation(get()) }
    factory { PermissionMutation(get()) }
    factory { SystemUtilityMutation(get()) }
}

fun Application.configureDependencyInjection() {
    val mongodbName = environment.config.property("database.mongodb.dbname").getString()
    val mongoSettings = databaseSettings()
    val mongoClient = MongoClients.create(mongoSettings)

    val dbCollectionModule = module {
        single<MongoClient> { mongoClient }
        single<MongoDatabase> { mongoClient.getDatabase(mongodbName) }
    }

    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(
            monitoringModule,
            dbCollectionModule,
            contextFactoryModule,
            externalServiceModule,
            databaseInteractionModule, databaseRepositoryModule,
            webInteractionModule, webRepositoryModule,
            queryResolverModule, mutationResolverModule
        )
    }
}
