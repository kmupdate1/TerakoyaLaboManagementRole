package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.base.FirstName
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class FirstNameCodec: Codec<FirstName> {
    override fun encode(
        p0: BsonWriter,
        p1: FirstName,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<FirstName> = FirstName::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): FirstName = FirstName.init(p0?.readString()).getOrThrow()
}
