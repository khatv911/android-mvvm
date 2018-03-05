package com.kay.core.viewmodel

import android.arch.lifecycle.LifecycleOwner
import com.kay.core.utils.LoadingState

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
interface LifecycleOwnerExt<in T>: LifecycleOwner{

    /**
     * loading state change
     */
    fun onLoadingStateChanged(loadingState:LoadingState? = LoadingState.NORMAL)

    /**
     * interested com.kay.apptemplate.data
     */
    fun onDataChanged(data: T?)

    /**
     *
     */
    fun onError(throwable: Throwable?)

    /**
     * show or log success message?
     */
    fun onSuccess(message: String?)
}