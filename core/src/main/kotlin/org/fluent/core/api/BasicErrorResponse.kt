package org.fluent.core.api

data class BasicErrorResponse(
    override val status: Int,
    override val message: String
) : IAbstractResponse<String>