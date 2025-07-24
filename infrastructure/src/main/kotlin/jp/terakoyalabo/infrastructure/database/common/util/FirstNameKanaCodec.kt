package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.base.FirstNameKana
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class FirstNameKanaCodec: Codec<FirstNameKana> {
    override fun encode(
        p0: BsonWriter,
        p1: FirstNameKana,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<FirstNameKana> = FirstNameKana::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): FirstNameKana = FirstNameKana.init(p0?.readString()).getOrThrow()
}
