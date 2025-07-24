package jp.terakoyalabo.infrastructure.database.common.util

import jp.terakoyalabo.domain.value.extended.Country
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class CountryCodec: Codec<Country> {
    override fun encode(
        p0: BsonWriter,
        p1: Country,
        p2: EncoderContext,
    ) { p0.writeString(p1.toString()) }

    override fun getEncoderClass(): Class<Country> = Country::class.java

    override fun decode(
        p0: BsonReader?,
        p1: DecoderContext,
    ): Country = Country.init(p0?.readString()).getOrThrow()
}
