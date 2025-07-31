package jp.terakoyalabo.application.resolver.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import jp.terakoyalabo.application.schema.common.ResponseStatus
import jp.terakoyalabo.application.schema.input.PermissionInput
import jp.terakoyalabo.application.schema.common.ResponseType
import jp.terakoyalabo.common.constant.ContextKeys
import jp.terakoyalabo.domain.command.web.CreatePermissionCommand
import jp.terakoyalabo.domain.repository.web.PermissionRepository
import jp.terakoyalabo.domain.value.core.UserId
import jp.terakoyalabo.domain.value.permission.Description
import jp.terakoyalabo.domain.value.permission.DisplayName
import jp.terakoyalabo.domain.value.permission.Identifier
import jp.terakoyalabo.domain.value.permission.Name

class PermissionMutation(
    private val repository: PermissionRepository,
): Mutation {
    @GraphQLDescription("MUTATION: Create permission.")
    suspend fun createPermission(input: PermissionInput, dfe: DataFetchingEnvironment): ResponseType = runCatching {
        val userId = UserId.init(dfe.graphQlContext.get<String>(ContextKeys.UserPrincipal.USER_ID))
            .getOrThrow()

        val name = Name.init(input.name).getOrThrow()
        val displayName = DisplayName.init(input.displayName).getOrThrow()
        val description = Description.init(input.description).getOrThrow()

        val command = CreatePermissionCommand(
            userId = userId,
            identifier = Identifier.issue(),
            name = name,
            displayName = displayName,
            description = description,
        )

        repository.create(command = command)
    }.fold(
        onSuccess = { ResponseType(status = ResponseStatus.SUCCESS) },
        onFailure = {
            ResponseType(
                status = ResponseStatus.FAILURE,
                message = "Failed to create permission: ${it.message}"
            )
        },
    )
}
