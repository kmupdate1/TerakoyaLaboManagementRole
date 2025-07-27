package jp.terakoyalabo.application.schema.type

import jp.terakoyalabo.application.schema.common.Data
import jp.terakoyalabo.application.schema.common.ResponseStatus

data class ResponseType(
    val status: ResponseStatus,
    val message: String? = null,
    val data: Data? = null,
)
