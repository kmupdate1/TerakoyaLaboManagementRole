package jp.terakoyalabo.application.resolver.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import jp.terakoyalabo.application.schema.common.PermissionListResponseType
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.type.PermissionType
import jp.terakoyalabo.domain.repository.web.PermissionRepository

class PermissionQuery(
    private val repository: PermissionRepository,
): Query {
    @GraphQLDescription("QUERY: Get permission")
    suspend fun getPermission(): PermissionListResponseType = runCatching {
        repository.findAll().map {
            PermissionType(
                identifier = it.identifier.toString(),
                name = it.name.toString(),
                displayName = it.displayName.toString(),
                description = it.description.toString(),
            )
        }
    }.fold(
        onSuccess = { PermissionListResponseType(status = ResponseStatus.SUCCESS, permissions = it) },
        onFailure = { PermissionListResponseType(status = ResponseStatus.FAILURE, message = "Failed to get permission: ${it.message}") },
    )
}
