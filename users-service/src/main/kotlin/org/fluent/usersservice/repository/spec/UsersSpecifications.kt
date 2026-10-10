package org.fluent.usersservice.repository.spec

import org.fluent.usersservice.entity.User
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class UsersSpecifications {
    fun hasName(name: String?): Specification<User>? = name?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.let { name ->
            Specification<User> { root, _, cb ->
                cb.like(cb.lower(root["name"]), "%${name.lowercase()}%")
            }
        }

    fun hasEmail(email: String?): Specification<User>? = email?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.let { email ->
            Specification<User> { root, _, cb ->
                cb.like(cb.lower(root["email"]), "%${email.lowercase()}%")
            }
        }
}