package org.fluent.core.api

import org.fluent.core.dto.IDtoClass

fun <T : IDtoClass> T.toHttpResponse(): BasicSuccessfulResponse<T> =
    BasicSuccessfulResponse(message = this)