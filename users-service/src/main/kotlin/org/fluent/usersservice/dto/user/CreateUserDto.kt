package org.fluent.usersservice.dto.user

import org.fluent.core.dto.IDtoClass

data class CreateUserDto(
    val name: String,
    val email: String,
    val password: String,
) : IDtoClass

