package org.fluent.usersservice.dto.user

import org.fluent.core.dto.IDtoClass

data class UpdateUserDto(
    val name: String?,
    val email: String?,
    val password: String?,
) : IDtoClass
