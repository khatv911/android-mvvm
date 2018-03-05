package com.kay.core.viewmodel

/**
 * Created by KhaTran on 31/1/18.
 */
interface HasViewModel<T, out VM: AbsBaseViewModel<T>>{
    /**
     *
     */
    fun getViewModel():VM


}