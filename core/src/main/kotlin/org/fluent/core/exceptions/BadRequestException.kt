package org.fluent.core.exceptions

class BadRequestException(
    override val message: String,
) : AbstractHttpException(statusCode = 400, message = message)