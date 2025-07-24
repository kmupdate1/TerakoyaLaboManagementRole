package jp.terakoyalabo.domain.value.base

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class FamilyName private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<FamilyName> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(FamilyName(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { familyName ->
            familyName.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Family name is required.")
    }
}
