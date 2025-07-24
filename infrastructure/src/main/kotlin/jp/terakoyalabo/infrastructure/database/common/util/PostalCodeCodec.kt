package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.extended.PostalCode
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class PostalCodeCodec: Codec<PostalCode> {
    override fun encode(
        p0: BsonWriter,
        p1: PostalCode,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<PostalCode> = PostalCode::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): PostalCode = PostalCode.init(p0?.readString()).getOrThrow()
}
