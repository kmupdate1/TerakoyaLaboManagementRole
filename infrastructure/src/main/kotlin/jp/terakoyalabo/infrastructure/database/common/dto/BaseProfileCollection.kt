package jp.terakoyalabo.infrastructure.database.common.dto

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class BaseProfileCollection(
    @BsonId
    @BsonProperty("_id") val baseProfileId: ObjectId = ObjectId(),
    @BsonProperty("user_id") val userId: String,
    @BsonProperty("first_name") val firstName: String,
    @BsonProperty("family_name") val familyName: String,
    @BsonProperty("first_name_kana") val firstNameKana: String,
    @BsonProperty("family_name_kana") val familyNameKana: String,
    @BsonProperty("display_name") val displayName: String,
    @BsonProperty("disabled") val disabled: Boolean = false,
    @BsonProperty("created_at") val createdAt: Long,
    @BsonProperty("updated_at") val updatedAt: Long,
    @BsonProperty("created_by") val createdBy: String,
    @BsonProperty("updated_by") val updatedBy: String,
)
