package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.input.BaseProfileInput
import jp.terakoyalabo.application.schema.input.ExtendedProfileInput
import jp.terakoyalabo.application.schema.type.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.entity.BaseProfile
import jp.terakoyalabo.domain.entity.CoreInformation
import jp.terakoyalabo.domain.entity.ExtendedProfile
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.domain.value.base.*
import jp.terakoyalabo.domain.value.core.Email
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.extended.*

class UserInfoMutation(
    private val repository: UserInfoRepository,
): Mutation {
    @GraphQLDescription("MUTATION: Create an user information by jwt.")
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
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to create core information: ${it.message}") },
    )

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

    @GraphQLDescription("MUTATION: Create an user extended profile.")
    suspend fun createExtendedProfile(input: ExtendedProfileInput, dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val gender = Gender.init(input.gender).getOrThrow()
        val dateOfBirth = DateOfBirth.init(input.dateOfBirth).getOrThrow()
        val phoneNumber = PhoneNumber.init(input.phoneNumber).getOrThrow()
        val postalCode = PostalCode.init(input.postalCode).getOrThrow()
        val country = Country.init(input.country).getOrThrow()
        val occupation = Occupation.init(input.occupation).getOrThrow()
        val address = Address.init(input.address).getOrThrow()
        val selfIntroduction = SelfIntroduction.init(input.selfIntroduction).getOrThrow()
        val interests = mutableListOf<Interest>()
        input.interests?.forEach {
            val interest = Interest.init(it.toString()).getOrThrow()
            interests.add(interest)
        }

        val entity = ExtendedProfile(
            gender = gender,
            dateOfBirth = dateOfBirth,
            phoneNumber = phoneNumber,
            postalCode = postalCode,
            country = country,
            address = address,
            occupation = occupation,
            selfIntroduction = selfIntroduction,
            interests = interests,
        )

        repository.createExtendedProfile(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to create extended profile: ${it.message}") },
    )
}
