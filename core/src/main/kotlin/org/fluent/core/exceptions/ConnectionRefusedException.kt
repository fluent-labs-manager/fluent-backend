package org.fluent.core.exceptions

import org.fluent.core.enums.MicroserviceName

class ConnectionRefusedException(
    override val message: String,
    val serviceName: MicroserviceName,
) : AbstractHttpException(statusCode = 503, message = message)