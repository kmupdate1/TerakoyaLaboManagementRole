package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.input.BaseProfileInput
import jp.terakoyalabo.application.schema.type.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.entity.BaseProfile
import jp.terakoyalabo.domain.repository.database.BaseProfileRepository
import jp.terakoyalabo.domain.value.base.*
import jp.terakoyalabo.domain.value.core.UserId

class BaseProfileMutation(
    private val repository: BaseProfileRepository,
): Mutation {
    @GraphQLDescription("MUTATION: Create an user base profile.")
    suspend fun createBaseProfile(input: BaseProfileInput, dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val firstName = FirstName.init(input.firstName).getOrThrow()
        val familyName = FamilyName.init(input.familyName).getOrThrow()
        val firstNameKana = FirstNameKana.init(input.firstNameKana).getOrThrow()
        val familyNameKana = FamilyNameKana.init(input.familyNameKana).getOrThrow()
        val displayName = DisplayName.init(input.displayName).getOrThrow()

        val entity = BaseProfile(
            firstName = firstName,
            familyName = familyName,
            firstNameKana = firstNameKana,
            familyNameKana = familyNameKana,
            displayName = displayName,
        )

        repository.createBaseProfile(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to create base profile: ${it.message}") },
    )

    @GraphQLDescription("MUTATION: Update an user base profile.")
    suspend fun updateBaseProfile(input: BaseProfileInput, dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val firstName = FirstName.init(input.firstName).getOrThrow()
        val familyName = FamilyName.init(input.familyName).getOrThrow()
        val firstNameKana = FirstNameKana.init(input.firstNameKana).getOrThrow()
        val familyNameKana = FamilyNameKana.init(input.familyNameKana).getOrThrow()
        val displayName = DisplayName.init(input.displayName).getOrThrow()

        val entity = BaseProfile(
            firstName = firstName,
            familyName = familyName,
            firstNameKana = firstNameKana,
            familyNameKana = familyNameKana,
            displayName = displayName,
        )

        repository.updateBaseProfile(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to update base profile: ${it.message}") },
    )

    @GraphQLDescription("MUTATION: Delete logically an user base information.")
    suspend fun deleteLogicallyBaseProfile(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        repository.deleteLogicallyBaseProfile(userId = userId)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to delete logically base profile: ${it.message}") },
    )

    @GraphQLDescription("MUTATION: Delete physically an user base information.")
    suspend fun deletePhysicallyBaseProfile(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        repository.deleteLogicallyBaseProfile(userId = userId)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to delete physically base profile: ${it.message}") },
    )
}
