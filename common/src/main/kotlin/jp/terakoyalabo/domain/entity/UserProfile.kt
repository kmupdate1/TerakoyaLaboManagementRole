package jp.terakoyalabo.domain.entity

import jp.terakoyalabo.domain.value.Email
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class UserProfile @BsonCreator constructor(
    @BsonId
    @BsonProperty("_id") val userInfoId: ObjectId = ObjectId(),
    @BsonProperty("user_id") val userId: String,
    @BsonProperty("email") val email: Email,
    @BsonProperty("first_name") val firstName: String,
    @BsonProperty("last_name") val lastName: String,
    @BsonProperty("created_at") val createdAt: Long,
    @BsonProperty("updated_at") val updatedAt: Long,
    @BsonProperty("created_by") val createdBy: String,
    @BsonProperty("updated_by") val updatedBy: String,
)
