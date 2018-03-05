package com.kay.core.error

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
interface UiResolverFactory<in T> {
    fun create(t: T): UiResolver
}