package jp.terakoyalabo.infrastructure.database.repository

import jp.terakoyalabo.infrastructure.database.common.dto.BaseProfileCollection
import jp.terakoyalabo.infrastructure.database.common.dto.CoreInformationCollection
import jp.terakoyalabo.infrastructure.database.common.dto.ExtendedProfileCollection
import jp.terakoyalabo.infrastructure.database.interaction.UserInfoInteraction
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentNotFoundException
import jp.terakoyalabo.domain.entity.BaseProfile
import jp.terakoyalabo.domain.entity.CoreInformation
import jp.terakoyalabo.domain.entity.ExtendedProfile
import jp.terakoyalabo.domain.repository.database.UserInfoRepository
import jp.terakoyalabo.domain.value.base.*
import jp.terakoyalabo.domain.value.core.Email
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.extended.*

class UserInfoRepositoryImpl(
    private val interaction: UserInfoInteraction,
): UserInfoRepository {
    override suspend fun createCoreInformation(userId: UserId, entity: CoreInformation) {
        val now = System.currentTimeMillis()

        val collection = CoreInformationCollection(
            userId = userId.toString(),
            email = entity.email.toString(),
            emailVerified = entity.emailVerified,
            signInProvider = entity.signInProvider,
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.createCoreInformation(collection = collection)?.run {}
            ?: throw DocumentCreateFailedException("Failed to create user core information.")
    }

    override suspend fun createBaseProfile(userId: UserId, entity: BaseProfile) {
        val now = System.currentTimeMillis()

        val collection = BaseProfileCollection(
            userId = userId.toString(),
            firstName = entity.firstName.toString(),
            familyName = entity.familyName.toString(),
            firstNameKana = entity.firstNameKana.toString(),
            familyNameKana = entity.familyNameKana.toString(),
            displayName = entity.displayName.toString(),
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.createBaseProfile(collection = collection)?.run {}
            ?: throw DocumentNotFoundException("Failed to create base profile.")
    }

    override suspend fun createExtendedProfile(userId: UserId, entity: ExtendedProfile) {
        val now = System.currentTimeMillis()

        val collection = ExtendedProfileCollection(
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
            createdAt = now,
            updatedAt = now,
            createdBy = userId.toString(),
            updatedBy = userId.toString(),
        )

        return interaction.createExtendedProfile(collection = collection)?.run {}
            ?: throw DocumentCreateFailedException("Failed to create extended profile.")
    }

    override suspend fun readCoreInformation(userId: UserId): CoreInformation =
        interaction.readCoreInformation(userId = userId.toString())?.let {
            val email = Email.init(it.email).getOrThrow()

            CoreInformation(
                email = email,
                emailVerified = it.emailVerified,
                signInProvider = it.signInProvider,
            )
        } ?: throw DocumentNotFoundException("No user core information found.")

    override suspend fun readBaseProfile(userId: UserId): BaseProfile =
        interaction.readBaeProfile(userId = userId.toString())?.let {
            val firstName = FirstName.init(it.firstName).getOrThrow()
            val familyName = FamilyName.init(it.familyName).getOrThrow()
            val firstNameKana = FirstNameKana.init(it.firstNameKana).getOrThrow()
            val familyNameKana = FamilyNameKana.init(it.familyNameKana).getOrThrow()
            val displayName = DisplayName.init(it.displayName).getOrThrow()

            BaseProfile(
                firstName = firstName,
                familyName = familyName,
                firstNameKana = firstNameKana,
                familyNameKana = familyNameKana,
                displayName = displayName,
            )
        } ?: throw DocumentNotFoundException("No base profile found.")

    override suspend fun readExtendedProfile(userId: UserId): ExtendedProfile =
        interaction.readExtendedProfile(userId = userId.toString())?.let {
            val gender = Gender.init(it.gender).getOrThrow()
            val dateOfBirth = DateOfBirth.init(it.dateOfBirth).getOrThrow()
            val phoneNumber = PhoneNumber.init(it.phoneNumber).getOrThrow()
            val postalCode = PostalCode.init(it.postalCode).getOrThrow()
            val country = Country.init(it.country).getOrThrow()
            val address = Address.init(it.address).getOrThrow()
            val occupation = Occupation.init(it.occupation).getOrThrow()
            val selfIntroduction = SelfIntroduction.init(it.selfIntroduction).getOrThrow()
            val interests = it.interests.map { interest -> Interest.init(interest).getOrThrow() }

            ExtendedProfile(
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
        } ?: throw DocumentNotFoundException("No extended profile found.")
}
