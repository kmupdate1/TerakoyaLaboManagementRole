package jp.terakoyalabo.presentation.factory

import com.expediagroup.graphql.generator.extensions.plus
import com.expediagroup.graphql.server.ktor.KtorGraphQLContextFactory
import graphql.GraphQLContext
import io.ktor.server.request.*
import jp.lax256.apigateway.core.constant.JwtPayloadKey
import jp.terakoyalabo.common.constant.ContextKeys

class ManagementRoleContextFactory: KtorGraphQLContextFactory() {
    override suspend fun generateContext(request: ApplicationRequest): GraphQLContext =
        super.generateContext(request).plus(
            graphQLContext = request.call.attributes[JwtPayloadKey].let { jwtPayload ->
                GraphQLContext.newContext()
                    .put(ContextKeys.UserPrincipal.USER_ID, jwtPayload.userId)
                    .put(ContextKeys.UserPrincipal.EMAIL, jwtPayload.email)
                    .put(ContextKeys.UserPrincipal.EMAIL_VERIFIED, jwtPayload.emailVerified)
                    .put(ContextKeys.UserPrincipal.AUTH_TIME, jwtPayload.authTime)
                    .put(ContextKeys.UserPrincipal.SIGN_IN_PROVIDER, jwtPayload.firebase.signInProvider)
                    .build()
            },
        )
}
