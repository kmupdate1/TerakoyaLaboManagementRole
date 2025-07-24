package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.extended.DateOfBirth
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class DateOfBirthCodec: Codec<DateOfBirth> {
    override fun encode(
        p0: BsonWriter,
        p1: DateOfBirth,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<DateOfBirth> = DateOfBirth::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): DateOfBirth = DateOfBirth.init(p0?.readString()).getOrThrow()
}
