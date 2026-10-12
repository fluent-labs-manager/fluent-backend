package org.fluent.usersservice.repository.mapper

import org.fluent.core.enums.UserRoles
import org.fluent.usersservice.entity.User
import org.fluent.usersservice.entity.UserRole
import org.fluent.usersservice.entity.UserRoleId
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserRoleMapper {

    fun userRoleFromUserAndRole(user: User, role: UserRoles): UserRole =
        UserRole().apply {
            this.user = user
            id = UserRoleId(
                userId = requireNotNull(user.id) { "Cannot assign a role to a user without an ID" },
                role = role,
            )
        }
}
