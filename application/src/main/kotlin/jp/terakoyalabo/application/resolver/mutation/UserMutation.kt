package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.collection.UserProfile
import jp.terakoyalabo.domain.graphql.input.UserInfoInput
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.domain.value.Email

class UserMutation(
    private val repository: UserInfoRepository,
): Mutation {
    @GraphQLDescription("MUTATION: Create an user information by jwt.")
    suspend fun createProfile(input: UserInfoInput, dfe: DataFetchingEnvironment): String? {
        val email = Email.init(dfe.graphQlContext.get(ContextKeys.UserPrincipal.EMAIL))
            .getOrThrow()
        val userId = dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID)
        val now = System.currentTimeMillis()

        val entity = UserProfile(
            userId = userId,
            email = email,
            firstName = input.firstName,
            lastName = input.lastName,
            createdAt = now,
            updatedAt = now,
            createdBy = userId,
            updatedBy = userId,
        )

        return repository.createProfile(entity = entity).toString()
    }
}
