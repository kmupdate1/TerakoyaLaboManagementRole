package jp.terakoyalabo.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import io.ktor.server.application.*
import jp.terakoyalabo.infrastructure.database.common.util.MongoDBMonitoring
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import java.util.concurrent.TimeUnit

fun Application.databaseSettings(): MongoClientSettings {
    val mongodbConfig = environment.config.config("database.mongodb")

    val mongodbUri = mongodbConfig.property("uri").getString()
    val mongodbName = mongodbConfig.property("dbname").getString()

    val pojoCodecProvider = PojoCodecProvider.builder().apply {
        register("jp.terakoya.infrastructure.database.common.dto")
        automatic(true)
    }.build()

    val codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            pojoCodecProvider,
        ),
        CodecRegistries.fromCodecs(),
    )

    return MongoClientSettings.builder().apply {
        applyConnectionString(ConnectionString(mongodbUri))
        addCommandListener(MongoDBMonitoring())
        codecRegistry(codecRegistry)
        timeout(10_000, TimeUnit.MILLISECONDS)
        applyToSslSettings { sslSettingsBuilder ->
            sslSettingsBuilder.apply {
                enabled(false)
            }.build()
        }
    }.build()
}
