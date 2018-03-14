package com.kay.core.viewmodel.simple

import com.kay.core.viewmodel.LifecycleOwnerExt


/**
 * Created by Kay Tran on 13/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
interface SimpleLifecycleOwner<in T> : LifecycleOwnerExt {
    fun onDataChanged(t: T?)
}