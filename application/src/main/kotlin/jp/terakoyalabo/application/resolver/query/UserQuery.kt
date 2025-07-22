package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.graphql.input.UserInfoInput
import jp.terakoyalabo.domain.graphql.type.UserProfileType

class UserQuery: Query {
    @GraphQLDescription("QUERY: Gets an user information by jwt.")
    fun myProfile(input: UserInfoInput, dfe: DataFetchingEnvironment): UserProfileType =
        UserProfileType(
            userId = dfe.graphQlContext.get(ContextKeys.UserPrincipal.USER_ID),
            email = dfe.graphQlContext.get(ContextKeys.UserPrincipal.EMAIL),
            firstName = input.firstName,
            lastName = input.lastName,
            status = "Active",
            lastLogin = dfe.graphQlContext.get(ContextKeys.UserPrincipal.AUTH_TIME),
        )
}
