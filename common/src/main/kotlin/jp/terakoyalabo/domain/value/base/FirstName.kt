package jp.terakoyalabo.domain.value.base

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class FirstName private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<FirstName> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(FirstName(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { firstName ->
            firstName.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("First name is required.")
    }
}
