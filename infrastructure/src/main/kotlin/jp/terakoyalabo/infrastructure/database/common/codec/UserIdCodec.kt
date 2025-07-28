package jp.terakoyalabo.infrastructure.database.common.codec

import jp.terakoyalabo.domain.value.core.UserId
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class UserIdCodec: Codec<UserId> {
    override fun encode(
        p0: BsonWriter,
        p1: UserId,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<UserId> = UserId::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): UserId = UserId.init(p0?.readString()).getOrThrow()
}
