package com.kay.core.livedata

import android.arch.lifecycle.LiveData
import com.kay.core.network.RequestState
import com.kay.core.utils.Retry
import retrofit2.Call
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class BaseRepository {

//    val primaryCoroutineExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, e ->
//        e.printStackTrace()
//        state.value = RequestState.ERROR(e)
//
//    }
//    val secondaryCoroutineExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
//        throwable.printStackTrace()
//        throw throwable
//    }

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
            Timber.d("Network fetch success")
            state.value = RequestState.SUCCESS("fetch success")
            this@BaseRepository.retry.value = null
        }

        override fun onFetchFailed(httpException: HttpException) {
            state.value = RequestState.ERROR(httpException)
            this@BaseRepository.retry.value = retry
        }

        override fun onException(e: Throwable) {
            state.value = RequestState.ERROR(e)
            this@BaseRepository.retry.value = retry
        }

        override fun onShouldNotFetch() {
            state.value = RequestState.DONE()
            this@BaseRepository.retry.value = null
        }
    }.asLiveData()

}