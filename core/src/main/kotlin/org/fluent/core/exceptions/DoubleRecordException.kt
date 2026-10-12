package org.fluent.core.exceptions

class DoubleRecordException(
    override val message: String,
) : AbstractHttpException(statusCode = 409, message = message)