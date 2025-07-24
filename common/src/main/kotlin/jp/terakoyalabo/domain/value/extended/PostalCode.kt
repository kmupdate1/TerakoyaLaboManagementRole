package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class PostalCode private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<PostalCode> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(PostalCode(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?) = input?.let { postalCode ->
            postalCode.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Postal Code is required.")
    }
}
