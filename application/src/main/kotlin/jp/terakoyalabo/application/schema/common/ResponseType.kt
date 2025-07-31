package jp.terakoyalabo.application.schema.common

import jp.terakoyalabo.application.schema.type.PermissionType

data class ResponseType(
    val status: ResponseStatus,
    val message: String? = null,
    val data: Data? = null,
)

data class PermissionListResponseType(
    val status: ResponseStatus,
    val message: String? = null,
    val permissions: List<PermissionType>? = null,
)
