package org.fluent.usersservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.f4b6a3.uuid.UuidCreator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import org.fluent.usersservice.annotation.NotTrimmable
import org.fluent.usersservice.util.TrimEntityListener
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class, TrimEntityListener::class)
class User(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @JsonIgnore
    @NotTrimmable
    @Column(nullable = false)
    var password: String,

    @CreatedDate
    var createdAt: Instant?,

    @LastModifiedDate
    var updatedAt: Instant?,

    @Id
    var id: UUID? = null,
) {
    @PrePersist
    fun generateId() {
        if (id == null) {
            id = UuidCreator.getTimeOrderedEpoch()
        }
    }

    override fun toString(): String {
        return "User(id=$id, email='$email', name='$name', createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
