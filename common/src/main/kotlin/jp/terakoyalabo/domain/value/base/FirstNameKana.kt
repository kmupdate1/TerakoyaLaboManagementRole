package jp.terakoyalabo.domain.value.base

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class FirstNameKana private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<FirstNameKana> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(FirstNameKana(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { kana ->
            kana.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Kana of First name is required.")
    }
}
