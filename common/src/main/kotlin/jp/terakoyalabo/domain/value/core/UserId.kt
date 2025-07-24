package jp.terakoyalabo.domain.value.core

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class UserId private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<UserId> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(UserId(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { userId ->
            userId.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Missing user_id.")
    }
}
