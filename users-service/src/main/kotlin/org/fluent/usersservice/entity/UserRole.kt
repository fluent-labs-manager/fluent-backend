package org.fluent.usersservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.fluent.usersservice.util.TrimEntityListener
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "user_roles")
@EntityListeners(AuditingEntityListener::class, TrimEntityListener::class)
class UserRole {

    @EmbeddedId
    lateinit var id: UserRoleId

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    lateinit var user: User
}
