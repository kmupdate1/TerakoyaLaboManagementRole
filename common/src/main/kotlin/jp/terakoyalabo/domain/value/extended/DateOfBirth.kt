package jp.terakoyalabo.domain.value.extended

import jp.terakoyalabo.common.exception.domain.InvalidFormatException
import java.time.LocalDate

@JvmInline
value class DateOfBirth private constructor(private val value: String){
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<DateOfBirth> =
            runCatching { validatedInput(input) }.fold(
                onSuccess = { Result.success(DateOfBirth(value = it.toString())) },
                onFailure = { Result.failure(exception = it) },
            )

        private fun validatedInput(input: String?): LocalDate = input?.let {
            LocalDate.parse(it)
        } ?: throw InvalidFormatException("LocalDate is required.")
    }
}
