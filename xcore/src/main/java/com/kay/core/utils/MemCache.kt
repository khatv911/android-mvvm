package com.kay.core.utils

import android.support.v4.util.LruCache

/**
 * Created by none on 3/2/18.
 */
object MemCache {
    private val maxMemory = Runtime.getRuntime().maxMemory() / 1024

    private val cache = LruCache<Any, Any>((maxMemory / 8).toInt())

    operator fun set(key: Any, value: Any) {
        cache.put(key, value)
    }

    operator fun get(key: Any): Any? = cache.get(key)

    fun remove(key: Any): Boolean = cache.remove(key) != null

}