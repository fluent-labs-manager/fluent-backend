package org.fluent.api

interface IAbstractResponse<T> {
    val status: Int
    val message: T?
}