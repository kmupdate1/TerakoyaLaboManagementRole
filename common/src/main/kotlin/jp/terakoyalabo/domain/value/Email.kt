package jp.terakoyalabo.domain.value

import jp.terakoyalabo.common.exception.domain.InvalidFormatException
import org.apache.commons.validator.routines.EmailValidator

@JvmInline
value class Email private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        private val validator: EmailValidator = EmailValidator.getInstance()

        fun init(input: String?): Result<Email> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Email(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        private fun validatedInput(input: String?) = input?.let { email ->
            email.takeIf { validator.isValid(it) } ?: throw InvalidFormatException("Email format is not valid.")
        } ?: throw InvalidFormatException("Email is required.")
    }
}
