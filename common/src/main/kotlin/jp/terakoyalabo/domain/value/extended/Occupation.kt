package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class Occupation private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Occupation> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Occupation(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { occupation ->
            occupation.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Occupation is required.")
    }
}
