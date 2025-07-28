package jp.terakoyalabo.infrastructure.database.common.codec

import jp.terakoyalabo.domain.value.base.DisplayName
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class DisplayNameCodec: Codec<DisplayName> {
    override fun encode(
        p0: BsonWriter,
        p1: DisplayName,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<DisplayName> = DisplayName::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): DisplayName = DisplayName.init(p0?.readString()).getOrThrow()
}
