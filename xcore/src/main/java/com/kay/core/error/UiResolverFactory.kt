package com.kay.core.error

/**
 * Created by none on 28/2/18.
 */
interface UiResolverFactory<in T> {
    fun create(t: T): UiResolver
}