package org.fluent.core.exceptions

class BadRequestException(
    override val message: String,
    override val traceId: String
) : AbstractHttpException(statusCode = 400, message = message, traceId = traceId)