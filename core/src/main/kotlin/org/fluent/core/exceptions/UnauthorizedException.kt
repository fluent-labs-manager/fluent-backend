package org.fluent.core.exceptions

class UnauthorizedException(
    override val message: String,
) : AbstractHttpException(statusCode = 401, message = message)