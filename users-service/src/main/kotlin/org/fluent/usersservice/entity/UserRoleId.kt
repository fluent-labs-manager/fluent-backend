package org.fluent.usersservice.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.fluent.core.enums.UserRoles
import java.io.Serializable
import java.util.UUID

@Embeddable
data class UserRoleId(
    @Column(name = "user_id", nullable = false)
    var userId: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRoles? = null,
) : Serializable
