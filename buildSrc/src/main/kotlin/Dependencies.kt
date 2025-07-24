object Dependencies {
    object Kotlinx {
        object Serialization {
            const val JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX}"
        }
    }
    object Ktor {
        object Client {
            const val CORE = "io.ktor:ktor-client-core:${Versions.KTOR}"
            const val CIO = "io.ktor:ktor-client-cio:${Versions.KTOR}"
        }
        object Server {
            const val CORE = "io.ktor:ktor-server-core:${Versions.KTOR}"
            const val CIO = "io.ktor:ktor-server-cio:${Versions.KTOR}"
            const val AUTH = "io.ktor:ktor-server-auth:${Versions.KTOR}"
            const val STATUS_PAGES = "io.ktor:ktor-server-status-pages:${Versions.KTOR}"
        }
    }
    object GraphQL {
        object Kotlin {
            const val KTOR_SERVER = "com.expediagroup:graphql-kotlin-ktor-server:${Versions.GRAPHQL}"
            const val SCHEMA_GENERATOR = "com.expediagroup:graphql-kotlin-schema-generator:${Versions.GRAPHQL}"
        }
    }
    object MongoDB {
        const val DRIVER_CORE = "org.mongodb:mongodb-driver-core:${Versions.MONGODB}"
        const val DRIVER_SYNC = "org.mongodb:mongodb-driver-sync:${Versions.MONGODB}"
        const val BSON = "org.mongodb:bson:${Versions.MONGODB}"
        const val BSON_KOTLINX = "org.mongodb:bson-kotlinx:${Versions.MONGODB}"
    }
    object KMongo {
        const val KMONGO = "org.litote.kmongo:kmongo:${Versions.KMONGO}"
        const val SERIALIZATION = "org.litote.kmongo:kmongo-serialization:${Versions.KMONGO}"

    }
    object Google {
        object Gcp {}
        object Firebase {
            const val ADMIN = "com.google.firebase:firebase-admin:${Versions.FIREBASE}"
        }
    }
    object Koin {
        const val CORE = "io.insert-koin:koin-core:${Versions.KOIN}"
        const val KTOR = "io.insert-koin:koin-ktor:${Versions.KOIN}"
        const val LOGGER_SLF4J = "io.insert-koin:koin-logger-slf4j:${Versions.KOIN}"
    }
    object Commons {
        const val VALIDATOR = "commons-validator:commons-validator:${Versions.VALIDATOR}"
    }
    object KtorApiGateway {
        object Lax256 {
            const val CORE = "jp.lax256:ktor-apigateway-core:${Versions.API_GATEWAY}"
            const val GCP = "jp.lax256:ktor-apigateway-gcp:${Versions.API_GATEWAY}"
        }
        object Kmupdate1 {
            const val CORE = "com.github.kmupdate1.ktor-apigateway:ktor-apigateway-core:${Versions.API_GATEWAY}"
            const val GCP = "com.github.kmupdate1.ktor-apigateway:ktor-apigateway-gcp:${Versions.API_GATEWAY}"
        }
    }
    object Slf4j {
        const val API = "org.slf4j:slf4j-api:${Versions.SLF4J}"
    }
    object Logback {
        const val CLASSIC = "ch.qos.logback:logback-classic:${Versions.LOGBACK}"
    }
}
