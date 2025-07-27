package jp.terakoyalabo.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import jp.terakoyalabo.infrastructure.database.common.util.MongoDBMonitoring
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import java.util.concurrent.TimeUnit

fun Application.configureDatabase(): MongoDatabase {
    val mongodbConfig = environment.config.config("database.mongodb")

    val mongodbUri = mongodbConfig.property("uri").getString()
    val mongodbName = mongodbConfig.property("dbname").getString()

    val pojoCodecProvider = PojoCodecProvider.builder().apply {
        automatic(true)
    }.build()

    val codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            pojoCodecProvider,
        ),
        CodecRegistries.fromCodecs(),
    )

    val settings = MongoClientSettings.builder().apply {
        applyConnectionString(ConnectionString(mongodbUri))
        addCommandListener(MongoDBMonitoring())
        codecRegistry(codecRegistry)
        timeout(10_000, TimeUnit.MILLISECONDS)
        applyToSslSettings { sslSettingsBuilder ->
            sslSettingsBuilder.apply {
                enabled(false)
            }
        }
    }.build()

    val mongoInstance = mongodbUri + mongodbName
    return runCatching {
        environment.log.info(
            "Attempting to connect to MongoDB instance '{}'.",
            mongoInstance,
        )

        MongoClients
            .create(settings)
            .getDatabase(mongodbName)
    }.fold(
        onSuccess = { mongoDatabase ->
            environment.log.info(
                "Successfully connected to MongoDB instance '{}'.",
                mongodbUri + mongoDatabase.name,
            )
            mongoDatabase
        },
        onFailure = { throwable ->
            environment.log.error(
                "Failed to connect to MongoDB instance '{}'.: {}",
                mongoInstance,
                throwable.message,
                throwable,
            )
            throw throwable
        },
    )
}
