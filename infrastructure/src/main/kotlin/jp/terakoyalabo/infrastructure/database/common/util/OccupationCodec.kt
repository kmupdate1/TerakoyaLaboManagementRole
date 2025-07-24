package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.extended.Occupation
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class OccupationCodec: Codec<Occupation> {
    override fun encode(
        p0: BsonWriter,
        p1: Occupation,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<Occupation> = Occupation::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): Occupation = Occupation.init(p0?.readString()).getOrThrow()
}
