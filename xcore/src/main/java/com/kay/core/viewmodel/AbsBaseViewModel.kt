package com.kay.core.viewmodel

import android.arch.lifecycle.*
import com.kay.core.utils.LoadingState
import com.kay.core.utils.RequestState
import com.kay.core.utils.Retry
import com.kay.core.utils.SingleLiveEvent


/**
 * Created by Kay Tran on 13/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
abstract class AbsBaseViewModel : ViewModel() {


    /**
     * Composite State Event
     */
    protected var mStateEvent = MediatorLiveData<RequestState>()

    /**
     * Composite Retry Event
     */
    protected val mRetryEvent = MediatorLiveData<Retry>()

    /**
     * tell view to show loading or not
     *
     */
    private val mLoadingEvent = SingleLiveEvent<@LoadingState.Value Int>()

    /**
     * tell view about error
     */
    private val mErrorEvent = SingleLiveEvent<Throwable>()


    private val mSuccessEvent = SingleLiveEvent<String>()


    private val liveDataSet = mutableSetOf<LiveData<*>>().apply {
        this += mLoadingEvent
        this += mErrorEvent
        this += mSuccessEvent
        // not quite necessary
        this += mStateEvent
        this += mRetryEvent
    }


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

    /**
     * Keep track of the livedata
     */
    internal fun manageLiveData(liveData: LiveData<*>) {
        liveDataSet += liveData
    }

    fun tearDown(lifecycleOwner: LifecycleOwner) {
        liveDataSet.forEach { it.removeObservers(lifecycleOwner) }
    }

    override fun onCleared() {
        liveDataSet.clear()
        super.onCleared()
    }

    fun setup(lifecycleOwnerExt: LifecycleOwnerExt) {
        /**
         * Take care of loading requestStateEvent
         */
        mLoadingEvent.observe(lifecycleOwnerExt, Observer { loading ->
            loading?.let { lifecycleOwnerExt.onLoadingStateChanged(it) }
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

        /**
         * No-Op
         */
        mStateEvent.observe(lifecycleOwnerExt, Observer {})

        /**
         * No-Op
         */
        mRetryEvent.observe(lifecycleOwnerExt, Observer { })
    }


}


operator fun <T> AbsBaseViewModel.plusAssign(liveData: LiveData<T>) {
    this.manageLiveData(liveData)
}

