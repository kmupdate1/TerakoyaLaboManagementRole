package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.base.FamilyName
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class FamilyNameCodec: Codec<FamilyName> {
    override fun encode(
        p0: BsonWriter,
        p1: FamilyName,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<FamilyName> = FamilyName::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): FamilyName = FamilyName.init(p0?.readString()).getOrThrow()
}
