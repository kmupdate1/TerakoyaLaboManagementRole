package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class Interest private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Interest> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Interest(value = it)) },
            onFailure = { Result.failure(exception = it) }
        )

        private fun validatedInput(input: String?): String = input?.let { interest ->
            interest.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Interest is required.")
    }
}
