package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.extended.Interest
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class InterestCodec: Codec<Interest> {
    override fun encode(
        p0: BsonWriter,
        p1: Interest,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<Interest> = Interest::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): Interest = Interest.init(p0?.readString()).getOrThrow()
}
