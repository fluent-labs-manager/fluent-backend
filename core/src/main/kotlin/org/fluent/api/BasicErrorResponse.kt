package org.fluent.api

data class BasicErrorResponse(
    override val status: Int,
    override val message: String
) : IAbstractResponse<String>