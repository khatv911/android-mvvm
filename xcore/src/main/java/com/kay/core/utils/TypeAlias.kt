package com.kay.core.utils

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
typealias ItemHandler<T> = (T) -> Unit

interface CanSetTitle {
    fun setActionBarTitle(title: String)
}

