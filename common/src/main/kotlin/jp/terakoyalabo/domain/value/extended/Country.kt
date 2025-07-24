package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class Country private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Country> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Country(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { country ->
            country.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Country is required.")
    }
}
