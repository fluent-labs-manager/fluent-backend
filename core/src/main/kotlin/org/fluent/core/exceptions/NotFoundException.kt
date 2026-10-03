package org.fluent.core.exceptions

class NotFoundException(
    override val message: String,
    override val traceId: String
) : AbstractHttpException(statusCode = 404, message = message, traceId = traceId)