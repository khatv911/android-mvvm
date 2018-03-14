package com.kay.core.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.kay.core.livedata.SingleLiveEvent
import com.kay.core.network.RequestState
import com.kay.core.utils.LoadingState


/**
 * Created by Kay Tran on 13/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
abstract class AbsBaseViewModel : ViewModel() {


    protected var mStateEvent = MediatorLiveData<RequestState>()

    /**
     * tell view to show loading or not
     *
     */
    private val mLoadingEvent = SingleLiveEvent<LoadingState?>()

    /**
     * tell view about error
     */
    private val mErrorEvent = SingleLiveEvent<Throwable>()


    private val mSuccessEvent = SingleLiveEvent<String>()


    protected fun setLoading() {
        mLoadingEvent.value = LoadingState.NORMAL
    }

    protected fun setRefreshing() {
        mLoadingEvent.value = LoadingState.REFRESHING
    }

    protected fun setLoadingMore() {
        mLoadingEvent.value = LoadingState.MORE
    }


    fun extractState(state: RequestState) {

        when (state) {
            RequestState.STARTED -> {
                // unused for now
            }
            is RequestState.DONE -> {
                mLoadingEvent.value = LoadingState.NONE
                when (state) {
                    is RequestState.SUCCESS -> {
                        setSuccessMessage(state.message)
                    }
                    is RequestState.ERROR -> {
                        setError(state.throwable)
                    }
                }
            }

        }
    }


    protected fun setError(throwable: Throwable) {
        mErrorEvent.value = throwable
    }

    protected fun setSuccessMessage(message: String?) {
        mSuccessEvent.value = message
    }

    fun setup(lifecycleOwnerExt: LifecycleOwnerExt) {
        /**
         * Take care of loading state
         */
        mLoadingEvent.observe(lifecycleOwnerExt, Observer {
            lifecycleOwnerExt.onLoadingStateChanged(it)
        })
        /**
         * Take care of error
         */
        mErrorEvent.observe(lifecycleOwnerExt, Observer {
            // error handler shall take are of this
            lifecycleOwnerExt.onError(it)
        })

        /**
         * Not so much useful
         */
        mSuccessEvent.observe(lifecycleOwnerExt, Observer {
            lifecycleOwnerExt.onSuccess(it)
        })

        mStateEvent.observe(lifecycleOwnerExt, Observer {
            // noOpt
        })
    }

}