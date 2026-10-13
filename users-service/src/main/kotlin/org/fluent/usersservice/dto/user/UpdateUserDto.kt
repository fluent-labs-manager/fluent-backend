package org.fluent.usersservice.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.fluent.core.dto.IDtoClass

data class UpdateUserDto(
    @field:Pattern(
        regexp = ".*\\S.*",
        message = "Имя не должно быть пустым"
    )
    val name: String?,

    @field:Email(message = "Некорректный email")
    val email: String?
) : IDtoClass
