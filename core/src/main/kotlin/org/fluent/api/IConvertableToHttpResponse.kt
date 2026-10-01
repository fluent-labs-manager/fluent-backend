package org.fluent.api

interface IConvertableToHttpResponse<T> {
    fun T.toHttpResponse(): BasicSuccessfulResponse<T> {
        return BasicSuccessfulResponse(this)
    }
}