package com.kay.core.utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */

// Needed for better type inference
fun <X, Y> LiveData<X>.map(func: (X) -> Y): LiveData<Y> =
        Transformations.map(this, func)

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>): LiveData<Y> =
        Transformations.switchMap(this, func)

inline fun <T> LiveData<T>.observeK(owner: LifecycleOwner, crossinline observer: (T?) -> Unit): LiveData<T> {
    this.observe(owner, Observer { observer(it) })
    return this
}



