package org.fluent.core.api

interface IConvertableToHttpResponse<T> {
    fun T.toHttpResponse(): BasicSuccessfulResponse<T> {
        return BasicSuccessfulResponse(this)
    }
}