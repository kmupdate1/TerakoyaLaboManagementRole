package jp.terakoyalabo.domain.value.permission

@JvmInline
value class Description private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: String?): Result<Description> = runCatching { validatedInput(input) }.fold(
            onSuccess = { Result.success(Description(value = it)) },
            onFailure = { Result.failure(exception = it) },
        )

        fun validatedInput(input: String?): String = input ?: ""
    }
}
