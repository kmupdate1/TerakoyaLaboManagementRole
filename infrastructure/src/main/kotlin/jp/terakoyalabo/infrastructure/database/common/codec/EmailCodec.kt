package jp.terakoyalabo.infrastructure.database.common.codec

import jp.terakoyalabo.domain.value.core.Email
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class EmailCodec: Codec<Email> {
    override fun encode(
        p0: BsonWriter,
        p1: Email,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<Email> = Email::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): Email = Email.init(p0?.readString()).getOrThrow()
}
