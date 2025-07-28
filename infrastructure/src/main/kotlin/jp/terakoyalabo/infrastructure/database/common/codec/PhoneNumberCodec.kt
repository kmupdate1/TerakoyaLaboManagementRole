package jp.terakoyalabo.infrastructure.database.common.codec

import jp.terakoyalabo.domain.value.extended.PhoneNumber
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class PhoneNumberCodec: Codec<PhoneNumber> {
    override fun encode(
        p0: BsonWriter,
        p1: PhoneNumber,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<PhoneNumber> = PhoneNumber::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): PhoneNumber = PhoneNumber.init(p0?.readString()).getOrThrow()
}
