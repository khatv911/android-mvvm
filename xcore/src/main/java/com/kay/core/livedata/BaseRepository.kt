package com.kay.core.livedata

import android.arch.lifecycle.LiveData
import com.kay.core.network.RequestState
import com.kay.core.utils.Retry
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import retrofit2.Call

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class BaseRepository {

    fun withRetryExceptionHandler(_retry: Retry) = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        state.value = RequestState.ERROR(e)
        retry.value = _retry
    }

    val state = SingleLiveEvent<RequestState>()

    val retry = SingleLiveEvent<Retry>()
    //TODO: convert this function into an extension
    fun <PersistedData, NetworkResponse : Any> createNetworkBoundResource(dbCall: LiveData<PersistedData?>, networkCall: Call<NetworkResponse>, persistNetworkResult: (NetworkResponse) -> Unit, shouldFetch: (PersistedData?) -> Boolean):
            LiveData<PersistedData?> = object : NetworkBoundResource<PersistedData, NetworkResponse>() {
        override fun loadFromDB(): LiveData<PersistedData?> = dbCall

        override fun createCall(): Call<NetworkResponse> = networkCall

        override fun saveCallResult(requestType: NetworkResponse) = persistNetworkResult(requestType)

        override fun shouldFetch(resultType: PersistedData?): Boolean = shouldFetch(resultType)

        override fun onFetchSuccess() {
            state.value = RequestState.SUCCESS("New Updated")
            this@BaseRepository.retry.value = null
        }

        override fun onException(e: Throwable) {
            state.value = RequestState.ERROR(e)
            this@BaseRepository.retry.value = retry
        }

        override fun noNeedFetching() {
            state.value = RequestState.DONE()
            this@BaseRepository.retry.value = null
        }
    }.asLiveData()

}