package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class Gender private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Gender> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Gender(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?) = input?.let { gender ->
            gender.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Gender is required.")
    }
}
