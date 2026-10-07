package org.fluent.core.exceptions

class DoubleRecordException(
    override val message: String,
    override val traceId: String
) : AbstractHttpException(statusCode = 409, message = message, traceId = traceId)