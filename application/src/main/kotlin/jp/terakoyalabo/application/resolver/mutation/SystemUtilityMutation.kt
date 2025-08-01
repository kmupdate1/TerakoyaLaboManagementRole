package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.common.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.entity.database.SigningHistory
import jp.terakoyalabo.domain.repository.database.SystemUtilityRepository
import jp.terakoyalabo.domain.value.core.UserId

class SystemUtilityMutation(
    private val repository: SystemUtilityRepository,
): Mutation {
    @GraphQLDescription("MUTATION: Memory user sign in history.")
    suspend fun attemptSigning(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val entity = SigningHistory(
            authTime = dfe.graphQlContext.get(ContextKeys.UserPrincipal.AUTH_TIME),
        )

        repository.recordSigningHistory(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to recode signing history: ${it.message}"
        ) }
    )
}
