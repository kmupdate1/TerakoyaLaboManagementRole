package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.type.ExtendedProfileType
import jp.terakoyalabo.application.schema.common.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.repository.database.ExtendedProfileRepository
import jp.terakoyalabo.domain.value.core.UserId

class ExtendedProfileQuery(
    private val repository: ExtendedProfileRepository,
): Query {
    @GraphQLDescription("QUERY: Get an user extended profile.")
    suspend fun getMyExtendedProfile(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val entity = repository.referenceExtendedProfile(userId = userId)

        ExtendedProfileType(
            userId = userId.toString(),
            gender = entity.gender.toString(),
            dateOfBirth = entity.dateOfBirth.toString(),
            phoneNumber = entity.phoneNumber.toString(),
            postalCode = entity.postalCode.toString(),
            country = entity.country.toString(),
            address = entity.address.toString(),
            occupation = entity.occupation.toString(),
            selfIntroduction = entity.selfIntroduction.toString(),
            interests = entity.interests.map { it.toString() },
        )
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS, data = it) },
        onFailure = { ResponseType(
            status = ResponseStatus.FAILURE,
            message = "Failed to get extended profile: ${it.message}"
        ) },
    )
}
