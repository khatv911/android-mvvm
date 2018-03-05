package com.kay.core.livedata

import android.arch.lifecycle.LiveData
import com.kay.core.network.RequestState
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import retrofit2.Call
import retrofit2.HttpException
import timber.log.Timber
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by none on 4/2/18.
 */
abstract class BaseRepository {

    val primaryCoroutineExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        state.value = RequestState.ERROR(e)

    }
    val secondaryCoroutineExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        throw throwable
    }

    val state = SingleLiveEvent<RequestState>()


    var retry: (() -> Any)? = null


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
            this@BaseRepository.retry = null
        }

        override fun onFetchFailed(httpException: HttpException) {
            state.value = RequestState.ERROR(httpException)
            this@BaseRepository.retry = retry
        }

        override fun onException(e: Throwable) {
            state.value = RequestState.ERROR(e)
            this@BaseRepository.retry = retry
        }

        override fun onShouldNotFetch() {
            Timber.d("no need to fetch, db data is valid")
            state.value = RequestState.SUCCESS("db data is good")
            this@BaseRepository.retry = null
        }
    }.asLiveData()

}