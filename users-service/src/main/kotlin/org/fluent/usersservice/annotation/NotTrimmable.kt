package org.fluent.usersservice.annotation

/**
 * Annotation to mark fields that should be excluded from automatic string trimming.
 *
 * When applied to a field, it prevents the [org.fluent.usersservice.util.TrimEntityListener] from trimming
 * leading and trailing whitespace. This is useful for fields where whitespace
 * is significant (e.g., passwords, formatted text, codes, or any field where
 * exact value preservation is required).
 *
 * ## Retention:
 * The annotation is retained at runtime ([java.lang.annotation.RetentionPolicy.RUNTIME]) so it can be
 * accessed via reflection by the [org.fluent.usersservice.util.TrimEntityListener].
 *
 * ## Target:
 * This annotation can only be applied to fields ([AnnotationTarget.FIELD]).
 *
 * @see org.fluent.usersservice.util.TrimEntityListener
 * @see AnnotationTarget.FIELD
 * @see java.lang.annotation.RetentionPolicy.RUNTIME
 *
 * @author Vladimir Fokin
 * @since 0.2.0
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class NotTrimmable
