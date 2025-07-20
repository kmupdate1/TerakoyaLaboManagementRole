object Dependencies {
    object Ktor {
        object Client {
            const val CORE = "io.ktor:ktor-client-core:${Versions.KTOR}"
            const val CIO = "io.ktor:ktor-client-cio:${Versions.KTOR}"
            const val CONTENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:${Versions.KTOR}"
        }
        object Server {
            const val CORE = "io.ktor:ktor-server-core:${Versions.KTOR}"
            const val CIO = "io.ktor:ktor-server-cio:${Versions.KTOR}"
            const val STATUS_PAGES = "io.ktor:ktor-server-status-pages:${Versions.KTOR}"
            const val OPENAPI = "io.ktor:ktor-server-openapi:${Versions.KTOR}"
            const val SWAGGER = "io.ktor:ktor-server-swagger:${Versions.KTOR}"
        }
    }
    object GraphQL {
        object Kotlin {
            const val KTOR_SERVER = "com.expediagroup:graphql-kotlin-ktor-server:${Versions.GRAPHQL}"
            const val SCHEMA_GENERATOR = "com.expediagroup:graphql-kotlin-schema-generator:${Versions.GRAPHQL}"
        }
    }
    object Koin {
        const val CORE = "io.insert-koin:koin-core:${Versions.KOIN}"
        const val KTOR = "io.insert-koin:koin-ktor:${Versions.KOIN}"
        const val LOGGER_SLF4J = "io.insert-koin:koin-logger-slf4j:${Versions.KOIN}"
    }
    object Logback {
        const val CLASSIC = "ch.qos.logback:logback-classic:${Versions.LOGBACK}"
    }
}
