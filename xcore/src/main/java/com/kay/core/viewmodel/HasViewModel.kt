package com.kay.core.viewmodel

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
interface HasViewModel<T, out VM: AbsBaseViewModel<T>>{
    /**
     *
     */
    fun getViewModel():VM


}