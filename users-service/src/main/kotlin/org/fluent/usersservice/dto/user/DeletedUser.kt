package org.fluent.usersservice.dto.user

import org.fluent.core.dto.IDtoClass
import java.util.UUID

data class DeletedUser(
    val id: UUID,
    val email: String,
) : IDtoClass
