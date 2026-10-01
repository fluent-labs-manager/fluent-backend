package org.fluent.exceptions

class UnauthorizedException(
    override val message: String,
    override val traceId: String
) : AbstractHttpException(statusCode = 401, message = message, traceId = traceId)