package com.kay.core.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import timber.log.Timber

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class NetworkBoundResource<PersistedData, NetworkResponse : Any> {


    private val result = MediatorLiveData<PersistedData?>()

    var retry: (() -> Any)? = null

    init {
        loadData()
    }

    private fun loadData() {

        /**
         * we first load from db or cache, as long as return a livedata
         */

        val dbSource = loadFromDB()
        result.addSource(dbSource, { data ->
            setValue(data)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                onShouldNotFetch()
            }
        })


    }

    fun asLiveData(): LiveData<PersistedData?> {
        return result
    }


    private fun setValue(newValue: PersistedData?) {
        if (result.value != newValue) result.value = newValue
    }


    private fun fetchFromNetwork(dbSource: LiveData<PersistedData?>) {
        launch(UI) {
            val networkResult = createCall().awaitResult()
            when (networkResult) {
                is Result.Ok -> {
                    retry = null
                    onFetchSuccess()
                    result.removeSource(dbSource)
                    async(CommonPool) { saveCallResult(networkResult.value) }.await()
                    result.addSource(loadFromDB(), { freshData -> setValue(freshData) })
                }
                is Result.Error -> {
                    Timber.e("Http Exception! ${networkResult.exception}")
                    retry = {
                        fetchFromNetwork(dbSource)
                    }
                    onFetchFailed(networkResult.exception)
                }
                is Result.Exception -> {
                    Timber.e("Shit happens! ${networkResult.exception}")
                    retry = {
                        fetchFromNetwork(dbSource)
                    }
                    onException(networkResult.exception)
                }
            }
        }
    }

    protected abstract fun loadFromDB(): LiveData<PersistedData?>

    protected abstract fun createCall(): Call<NetworkResponse>

    protected abstract fun saveCallResult(requestType: NetworkResponse)

    protected abstract fun shouldFetch(resultType: PersistedData?): Boolean

    protected abstract fun onFetchSuccess()

    protected abstract fun onFetchFailed(httpException: HttpException)

    protected abstract fun onException(e: Throwable)

    protected abstract fun onShouldNotFetch()


}