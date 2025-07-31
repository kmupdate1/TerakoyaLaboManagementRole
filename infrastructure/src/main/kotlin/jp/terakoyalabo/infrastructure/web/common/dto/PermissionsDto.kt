package jp.terakoyalabo.infrastructure.web.common.dto

import com.google.cloud.firestore.annotation.DocumentId
import com.google.cloud.firestore.annotation.PropertyName

data class PermissionsDto @JvmOverloads constructor (
    @DocumentId val documentId: String? = null,
    @PropertyName("identifier") val identifier: String,
    @PropertyName("name") val name: String,
    @PropertyName("display_name") val displayName: String,
    @PropertyName("description") val description: String? = null,
    @PropertyName("disabled") val disabled: Boolean = false,
    @PropertyName("created_at") val createdAt: Long,
    @PropertyName("updated_at") val updatedAt: Long,
    @PropertyName("created_by") val createdBy: String,
    @PropertyName("updated_by") val updatedBy: String,
)
