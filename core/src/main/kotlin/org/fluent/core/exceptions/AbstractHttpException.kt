package org.fluent.core.exceptions

abstract class AbstractHttpException(
    open val statusCode: Int,
    override val message: String,
) : RuntimeException(message)