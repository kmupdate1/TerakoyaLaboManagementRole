package test

object TestDependencies {
    object Junit {
        const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api:${TestVersions.JUNIT}"
        const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${TestVersions.JUNIT}"
    }
    object KMongo {
        const val KMONGO = "org.litote.kmongo:kmongo:${TestVersions.KMONGO}"
    }
    const val MOCKK = "io.mockk:mockk:${TestVersions.MOCKK}"
}
