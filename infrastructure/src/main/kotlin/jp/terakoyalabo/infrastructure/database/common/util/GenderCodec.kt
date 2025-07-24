package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.extended.Gender
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class GenderCodec: Codec<Gender> {
    override fun encode(
        p0: BsonWriter,
        p1: Gender,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<Gender> = Gender::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): Gender = Gender.init(p0.toString()).getOrThrow()
}
