package org.fluent.core.exceptions

class NotFoundException(
    override val message: String,
) : AbstractHttpException(statusCode = 404, message = message)