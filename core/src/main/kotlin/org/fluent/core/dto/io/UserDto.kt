package org.fluent.core.dto.io

import kotlinx.serialization.Serializable
import org.fluent.core.dto.IDtoClass
import org.fluent.core.enums.UserRoles
import org.fluent.core.serializers.UUIDSerializer
import java.util.UUID

@Serializable
data class UserDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val email: String,
    val roles: Set<UserRoles>,
) : IDtoClass