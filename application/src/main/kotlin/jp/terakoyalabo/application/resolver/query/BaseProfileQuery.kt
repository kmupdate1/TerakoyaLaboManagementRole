package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.type.BaseProfileType
import jp.terakoyalabo.application.schema.type.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.repository.database.BaseProfileRepository
import jp.terakoyalabo.domain.value.core.UserId

class BaseProfileQuery(
    private val repository: BaseProfileRepository,
) : Query {
    @GraphQLDescription("QUERY: Get an user base profile.")
    suspend fun getMyBaseProfile(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val entity = repository.referenceBaseProfile(userId = userId)

        BaseProfileType(
            userId = userId.toString(),
            firstName = entity.firstName.toString(),
            familyName = entity.familyName.toString(),
            firstNameKana = entity.firstNameKana.toString(),
            familyNameKana = entity.familyNameKana.toString(),
            displayName = entity.displayName.toString(),
        )
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS, data = it) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to get base profile: ${it.message}") },
    )
}
