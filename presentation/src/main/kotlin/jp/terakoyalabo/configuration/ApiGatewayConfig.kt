package jp.terakoyalabo.configuration

import io.ktor.server.application.*
import jp.lax256.apigateway.core.CloudVendor
import jp.lax256.apigateway.gcp.plugin.GcpApiGateway

fun Application.configureApiGateway() {
    install(GcpApiGateway) {
        issuePrincipal {
            issue = true
        }
        vendor = CloudVendor.GCP
    }
}
