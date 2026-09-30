package org.fluent.api

data class BasicSuccessfulResponse<T>(
    override val message: T,
    override val status: Int = 200,
) : IAbstractResponse<T>