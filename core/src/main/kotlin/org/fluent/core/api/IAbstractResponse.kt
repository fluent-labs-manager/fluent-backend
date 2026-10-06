package org.fluent.core.api

interface IAbstractResponse<T> {
    val status: Int
    val message: T?
}