package jp.terakoyalabo.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import jp.lax256.infrastructure.common.util.MongoDBMonitoring
import jp.terakoyalabo.common.util.codec.EmailCodec
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

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
        CodecRegistries.fromCodecs(
            EmailCodec(),
        ),
    )

    val settings = MongoClientSettings.builder().apply {
        applyConnectionString(ConnectionString(mongodbUri))
        addCommandListener(MongoDBMonitoring())
        codecRegistry(codecRegistry)
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
