package jp.terakoyalabo.infrastructure.database.common.dto

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class ExtendedProfileCollection(
    @BsonId
    @BsonProperty("_id") val extendedProfileId: ObjectId = ObjectId(),
    @BsonProperty("user_id") val userId: String,
    @BsonProperty("gender") val gender: String,
    @BsonProperty("date_of_birth") val dateOfBirth: String,
    @BsonProperty("phone_number") val phoneNumber: String,
    @BsonProperty("postal_code") val postalCode: String,
    @BsonProperty("country") val country: String,
    @BsonProperty("address") val address: String,
    @BsonProperty("occupation") val occupation: String,
    @BsonProperty("self_introduction") val selfIntroduction: String,
    @BsonProperty("interests") val interests: List<String>,
    @BsonProperty("disabled") val disabled: Boolean = false,
    @BsonProperty("created_at") val createdAt: Long,
    @BsonProperty("updated_at") val updatedAt: Long,
    @BsonProperty("created_by") val createdBy: String,
    @BsonProperty("updated_by") val updatedBy: String,
)
