package org.fluent.usersservice.util

import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.fluent.usersservice.annotation.NotTrimmable
import java.lang.reflect.Field
import kotlin.collections.forEach
import kotlin.reflect.KClass

/**
 * JPA entity listener that automatically trims whitespace from string fields before
 * database persistence operations.
 *
 * This listener intercepts JPA lifecycle events [@PrePersist] and [@PreUpdate] to
 * automatically trim leading and trailing whitespace from all string fields of an entity.
 *
 * @see PrePersist
 * @see PreUpdate
 * @see NotTrimmable
 * @see jakarta.persistence.EntityListeners
 *
 * @author Vladimir Fokin
 * @since 1.0
 */
class TrimEntityListener {

    /**
     * Cache storing pre-computed lists of trimmable string fields for each entity class.
     *
     * The cache uses the entity's [KClass] as key and stores a list of [Field] objects
     * that should be trimmed. This cache is populated lazily and persists for the
     * lifetime of the application, significantly improving performance by avoiding
     * repeated reflection operations.
     */
    private val stringFieldsCache = mutableMapOf<KClass<*>, List<Field>>()

    /**
     * Automatically invoked before an entity is persisted to the database.
     *
     * This lifecycle callback method triggers the string trimming process
     * before a new entity is inserted. It delegates to [trimStrings] to perform
     * the actual trimming operation.
     *
     * @param entity The entity about to be persisted
     */
    @PrePersist
    @PreUpdate
    fun trimStrings(entity: Any) {
        val entityClass = entity::class
        val fields = stringFieldsCache.getOrPut(entityClass) {
            entityClass.java.declaredFields
                .filter { it.type == String::class.java }
                .filter { it.getAnnotation(NotTrimmable::class.java) == null }
                .also { fields ->
                    fields.forEach { it.isAccessible = true }
                }
        }

        fields.forEach { field ->
            (field[entity] as? String)?.takeIf { it.isNotBlank() }?.let { trimmed ->
                field[entity] = trimmed.trim()
            }
        }
    }
}