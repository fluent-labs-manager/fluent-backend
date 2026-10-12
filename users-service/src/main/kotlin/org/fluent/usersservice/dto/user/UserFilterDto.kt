package org.fluent.usersservice.dto.user

import org.fluent.core.dto.IFilterDtoClass

data class UserFilterDto(
    val name: String?,
    val email: String?,
) : IFilterDtoClass
