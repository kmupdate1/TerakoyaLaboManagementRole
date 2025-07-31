package jp.terakoyalabo.domain.value.permission

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class DisplayName private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<DisplayName> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(DisplayName(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        fun validatedInput(input: String?): String = input?.let { displayName ->
            displayName.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Display name is required.")
    }
}
