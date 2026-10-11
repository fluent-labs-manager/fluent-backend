package org.fluent.core.exceptions

class ForbiddenException(
    override val message: String,
) : AbstractHttpException(statusCode = 403, message = message)