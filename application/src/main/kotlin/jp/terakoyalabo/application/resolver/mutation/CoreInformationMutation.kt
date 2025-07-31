package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.common.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.entity.database.CoreInformation
import jp.terakoyalabo.domain.repository.database.CoreInformationRepository
import jp.terakoyalabo.domain.value.core.Email
import jp.terakoyalabo.domain.value.core.UserId

class CoreInformationMutation(
    private val repository: CoreInformationRepository,
): Mutation {
    @GraphQLDescription("MUTATION: Create an user core information by jwt.")
    suspend fun createCoreInformation(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val email = Email.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.EMAIL))
            .getOrThrow()

        val entity = CoreInformation(
            email = email,
            emailVerified = dfe.graphQlContext.get(ContextKeys.UserPrincipal.EMAIL_VERIFIED),
            signInProvider = dfe.graphQlContext.get(ContextKeys.UserPrincipal.SIGN_IN_PROVIDER),
        )

        repository.createCoreInformation(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to create core information: ${it.message}"
        ) },
    )

    @GraphQLDescription("MUTATION: Update an user core information by jwt.")
    suspend fun updateCoreInformation(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val email = Email.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.EMAIL))
            .getOrThrow()

        val entity = CoreInformation(
            email = email,
            emailVerified = dfe.graphQlContext.get(ContextKeys.UserPrincipal.EMAIL_VERIFIED),
            signInProvider = dfe.graphQlContext.get(ContextKeys.UserPrincipal.SIGN_IN_PROVIDER),
        )

        repository.updateCoreInformation(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to update core information: ${it.message}"
        ) },
    )

    @GraphQLDescription("MUTATION: Delete logically an user core information by jwt.")
    suspend fun deleteLogicallyCoreInformation(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        repository.deleteLogicallyCoreInformation(userId = userId)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to delete logically core information: ${it.message}"
        ) },
    )

    @GraphQLDescription("MUTATION: Delete physically an user core information by jwt.")
    suspend fun deletePhysicallyCoreInformation(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        repository.deletePhysicallyCoreInformation(userId = userId)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to delete physically core information: ${it.message}"
        ) },
    )
}
