package com.kay.core.extension

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

/**
 * Created by none on 3/2/18.
 */

// Needed for better type inference
fun <X, Y> LiveData<X>.map(func: (X) -> Y): LiveData<Y> =
        Transformations.map(this, func)

fun <X, Y> LiveData<X>.smap(func: (X) -> LiveData<Y>): LiveData<Y> =
        Transformations.switchMap(this, func)