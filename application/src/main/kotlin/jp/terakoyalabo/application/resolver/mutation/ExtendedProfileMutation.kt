package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.input.ExtendedProfileInput
import jp.terakoyalabo.application.schema.type.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.entity.database.ExtendedProfile
import jp.terakoyalabo.domain.repository.database.ExtendedProfileRepository
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.extended.*

class ExtendedProfileMutation(
    private val repository: ExtendedProfileRepository,
): Mutation {
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

    @GraphQLDescription("MUTATION: Update an user extended profile.")
    suspend fun updateExtendedProfile(input: ExtendedProfileInput, dfe: DataFetchingEnvironment): ResponseType = runCatching {
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

        repository.updateExtendedProfile(userId = userId, entity = entity)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to update extended profile: ${it.message}") },
    )

    @GraphQLDescription("MUTATION: Delete logically an user extended profile.")
    suspend fun deleteLogicallyExtendedProfile(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        repository.deleteLogicallyExtendedProfile(userId = userId)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to delete logically extended profile: ${it.message}") },
    )

    @GraphQLDescription("MUTATION: Delete physically an user extended profile.")
    suspend fun deletePhysicallyExtendedProfile(dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        repository.deleteLogicallyExtendedProfile(userId = userId)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = { ResponseType(status = ResponseStatus.FAILURE, message = "Failed to delete physically extended profile: ${it.message}") },
    )
}
