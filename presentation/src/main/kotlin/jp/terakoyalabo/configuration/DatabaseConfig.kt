package jp.terakoyalabo.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import jp.terakoyalabo.common.util.codec.EmailCodec
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

fun Application.configureDatabase(): MongoDatabase {
    val mongodbConfig = environment.config.config("database.mongodb")

    val mongodbUri = mongodbConfig.property("uri").getString()
    val mongodbName = mongodbConfig.property("dbname").getString()

    val pojoCodecProvider = PojoCodecProvider.builder()
        .automatic(true)
        .build()

    val codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            pojoCodecProvider,
        ),
        CodecRegistries.fromCodecs(
            EmailCodec(),
        ),
    )

    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(mongodbUri))
        .codecRegistry(codecRegistry)
        .build()

    return MongoClients
        .create(settings)
        .getDatabase(mongodbName)
}
