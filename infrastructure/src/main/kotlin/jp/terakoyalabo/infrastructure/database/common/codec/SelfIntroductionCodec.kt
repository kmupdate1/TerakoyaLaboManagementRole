package jp.terakoyalabo.infrastructure.database.common.codec

import jp.terakoyalabo.domain.value.extended.SelfIntroduction
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class SelfIntroductionCodec: Codec<SelfIntroduction> {
    override fun encode(
        p0: BsonWriter,
        p1: SelfIntroduction,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<SelfIntroduction> = SelfIntroduction::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): SelfIntroduction = SelfIntroduction.init(p0?.readString()).getOrThrow()
}
