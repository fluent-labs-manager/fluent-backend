package org.fluent.core.exceptions

import org.fluent.core.enums.MicroserviceName

class ConnectionRefusedException(
    override val message: String,
    override val traceId: String,
    val serviceName: MicroserviceName,
) : AbstractHttpException(statusCode = 503, message = message, traceId = traceId)