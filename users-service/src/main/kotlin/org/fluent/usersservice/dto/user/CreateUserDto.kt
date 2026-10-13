package org.fluent.usersservice.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.fluent.core.dto.IDtoClass
import org.fluent.core.enums.UserRoles

data class CreateUserDto(
    @NotBlank
    val name: String,

    @NotBlank
    @Email(message = "Некорректный email")
    val email: String,

    @NotBlank
    val password: String,

    val role: UserRoles?,
) : IDtoClass

