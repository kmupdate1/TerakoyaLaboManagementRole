package jp.terakoyalabo.infrastructure.database.common.dto

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class CoreInformationCollection @BsonCreator constructor(
    @BsonProperty("_id") @BsonId val coreInformationId: ObjectId = ObjectId(),
    @BsonProperty("user_id") val userId: String,
    @BsonProperty("email") val email: String,
    @BsonProperty("email_verified") val emailVerified: Boolean,
    @BsonProperty("sign_in_provider") val signInProvider: String,
    @BsonProperty("disable") val disabled: Boolean = false,
    @BsonProperty("created_at") val createdAt: Long,
    @BsonProperty("updated_at") val updatedAt: Long,
    @BsonProperty("created_by") val createdBy: String,
    @BsonProperty("updated_by") val updatedBy: String,
)
