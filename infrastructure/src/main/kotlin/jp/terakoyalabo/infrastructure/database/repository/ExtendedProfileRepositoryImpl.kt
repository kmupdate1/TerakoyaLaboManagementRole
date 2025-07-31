package jp.terakoyalabo.infrastructure.database.repository

import jp.terakoyalabo.common.exception.infrastructure.CollectionOperationFailedException
import jp.terakoyalabo.domain.entity.database.ExtendedProfile
import jp.terakoyalabo.domain.repository.database.ExtendedProfileRepository
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.extended.*
import jp.terakoyalabo.infrastructure.database.common.dto.ExtendedProfileCollection
import jp.terakoyalabo.infrastructure.database.interaction.ExtendedProfileInteraction

class ExtendedProfileRepositoryImpl(
    private val interaction: ExtendedProfileInteraction,
): ExtendedProfileRepository {
    override suspend fun createExtendedProfile(
        userId: UserId,
        entity: ExtendedProfile,
    ) {
        val now = System.currentTimeMillis()

        val profile = ExtendedProfileCollection(
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

        return interaction.createExtendedProfile(profile = profile)?.run {}
            ?: throw CollectionOperationFailedException("Failed to create extended profile.")
    }

    override suspend fun referenceExtendedProfile(userId: UserId): ExtendedProfile =
        interaction.referenceExtendedProfile(userId = userId.toString())?.let {
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
        } ?: throw CollectionOperationFailedException("No extended profile found.")

    override suspend fun updateExtendedProfile(
        userId: UserId,
        entity: ExtendedProfile,
    ) {
        val now = System.currentTimeMillis()

        val profile = ExtendedProfileCollection(
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

        return interaction.updateExtendedProfile(profile = profile)?.run {}
            ?: throw CollectionOperationFailedException("Failed to update extended profile.")
    }

    override suspend fun deleteLogicallyExtendedProfile(userId: UserId) {
        val now = System.currentTimeMillis()

        return interaction.deleteLogicallyExtendedProfile(userId = userId.toString(), updatedAt = now)?.run {}
            ?: throw CollectionOperationFailedException("Failed to delete logically extended profile.")
    }

    override suspend fun deletePhysicallyExtendedProfile(userId: UserId) =
        interaction.deletePhysicallyExtendedProfile(userId = userId.toString())?.run {}
            ?: throw CollectionOperationFailedException("Failed to delete physically extended profile.")
}
