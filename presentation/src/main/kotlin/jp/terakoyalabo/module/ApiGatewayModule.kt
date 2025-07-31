package jp.terakoyalabo.module

import io.ktor.server.application.*
import jp.lax256.apigateway.core.CloudVendor
import jp.lax256.apigateway.gcp.plugin.GcpApiGateway

fun Application.configureApiGateway() {
    install(GcpApiGateway) {
        issuePrincipal {
            autoIssue = true
        }

        vendor = CloudVendor.GCP
    }
}
