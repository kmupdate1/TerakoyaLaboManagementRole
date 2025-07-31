package jp.terakoyalabo.domain.value.permission

import jp.terakoyalabo.common.exception.domain.InvalidFormatException

@JvmInline
value class Name private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Name> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Name(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        fun validatedInput(input: String?): String = input?.let { name ->
            name.takeIf { it.contains(":") }
                ?: throw InvalidFormatException("Name format must be '<do>:<what>'.")
        } ?: throw InvalidFormatException("Name is required.")
    }
}
