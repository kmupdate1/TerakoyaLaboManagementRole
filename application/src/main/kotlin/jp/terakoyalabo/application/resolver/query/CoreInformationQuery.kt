package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.type.CoreInformationType
import jp.terakoyalabo.application.schema.common.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.repository.database.CoreInformationRepository
import jp.terakoyalabo.domain.value.core.UserId

class CoreInformationQuery(
    private val repository: CoreInformationRepository,
): Query {
    @GraphQLDescription("QUERY: Get an user core information.")
    suspend fun getMyCoreInformation(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val entity = repository.referenceCoreInformation(userId = userId)

        CoreInformationType(
            userId = userId.toString(),
            email = entity.email.toString(),
            emailVerified = entity.emailVerified,
            signInProvider = entity.signInProvider,
        )
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS, data = it) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to get core information: ${it.message}"
        ) },
    )
}
