package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class Address private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Address> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Address(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?): String = input?.let { address ->
            address.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Address is required.")
    }
}
