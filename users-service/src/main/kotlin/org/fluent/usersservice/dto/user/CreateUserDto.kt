package org.fluent.usersservice.dto.user

import org.fluent.core.dto.IDtoClass
import org.fluent.core.enums.UserRoles

data class CreateUserDto(
    val name: String,
    val email: String,
    val password: String,
    val role: UserRoles?,
) : IDtoClass

