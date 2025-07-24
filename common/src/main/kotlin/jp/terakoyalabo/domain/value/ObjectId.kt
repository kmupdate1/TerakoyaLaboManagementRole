package jp.terakoyalabo.domain.value

import jp.terakoyalabo.common.exception.domain.InvalidFormatException
import org.bson.BsonValue

@JvmInline
value class ObjectId private constructor(private val value: String) {
    override fun toString(): String = value

    companion object {
        fun init(input: BsonValue?): ObjectId =
            ObjectId(run { validatedInput(input) })

        private fun validatedInput(input: BsonValue?) = input?.asObjectId()?.value?.toHexString()
            ?: throw InvalidFormatException("No Object ID found.")
    }
}
