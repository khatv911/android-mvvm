package com.kay.core.utils

        /**
         * Created by none on 10/2/18.
         */

typealias ItemHandler<T> = (T) -> Unit

interface CanSetTitle {
    fun setActionBarTitle(title: String)
}

