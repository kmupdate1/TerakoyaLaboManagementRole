package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class SelfIntroduction private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<SelfIntroduction> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(SelfIntroduction(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input
            ?: throw InvalidFormatException("Self introduction is required.")
    }
}
