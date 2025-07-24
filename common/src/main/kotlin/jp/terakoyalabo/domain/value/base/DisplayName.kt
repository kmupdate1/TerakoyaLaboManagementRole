package jp.terakoyalabo.domain.value.base

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class DisplayName private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<DisplayName> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(DisplayName(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input
            ?: throw InvalidFormatException("Display name is required.")
    }
}
