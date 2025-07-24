package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class PhoneNumber private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<PhoneNumber> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(PhoneNumber(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?) = input?.let { phoneNumber ->
            phoneNumber.takeIf { it.isNotEmpty() }
        } ?: throw InvalidFormatException("Phone Number is required.")
    }
}