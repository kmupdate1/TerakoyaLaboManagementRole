package jp.terakoyalabo.domain.value.permission

import jp.terakoyalabo.common.exception.domain.InvalidFormatException
import java.util.UUID

@JvmInline
value class Identifier private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun issue(): Identifier = Identifier(value = UUID.randomUUID().toString())

        fun init(input: String?): Result<Identifier> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Identifier(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        fun validatedInput(input: String?): String = input?.let { identifier ->
            UUID.fromString(identifier).toString()
        } ?: throw InvalidFormatException("Identifier is required.")
    }
}
