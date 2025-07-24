package jp.terakoyalabo.domain.value.base

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class FamilyNameKana private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<FamilyNameKana> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(FamilyNameKana(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { kana ->
            kana.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Kana of family name is required.")
    }
}
