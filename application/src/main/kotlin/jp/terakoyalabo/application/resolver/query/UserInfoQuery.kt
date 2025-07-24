package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.type.BaseProfileType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.domain.value.core.UserId

class UserInfoQuery(
    private val repository: UserInfoRepository,
) : Query {
    @GraphQLDescription("QUERY: Gets an user information by jwt.")
    suspend fun getMyBaseProfile(dfe: DataFetchingEnvironment): BaseProfileType {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val entity = repository.readBaseProfile(userId = userId)

        val type = BaseProfileType(
            firstName = entity.firstName.toString(),
            familyName = entity.familyName.toString(),
            firstNameKana = entity.firstNameKana.toString(),
            familyNameKana = entity.familyNameKana.toString(),
            displayName = entity.displayName.toString(),
        )

        return type
    }
}
