package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.common.exception.application.CollectionNotFoundException
import jp.terakoyalabo.domain.graphql.type.UserProfileType
import jp.terakoyalabo.domain.repository.database.UserInfoRepository

class UserQuery(
    private val repository: UserInfoRepository,
) : Query {
    @GraphQLDescription("QUERY: Gets an user information by jwt.")
    suspend fun myProfile(dfe: DataFetchingEnvironment): UserProfileType {
        val userId = dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID)

        println("user_id: $userId")

        val entity = repository.readProfile(userId = userId)
            ?: throw CollectionNotFoundException("No user profile found.")

        val userProfileType = UserProfileType(
            userId = entity.userId,
            email = entity.email.toString(),
            firstName = entity.firstName,
            lastName = entity.lastName,
        )

        return userProfileType
    }
}
