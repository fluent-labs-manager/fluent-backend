package org.fluent.core.exceptions

class ForbiddenException(
    override val message: String,
    override val traceId: String
) : AbstractHttpException(statusCode = 403, message = message, traceId = traceId)