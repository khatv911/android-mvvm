package com.kay.coin.view

import android.arch.lifecycle.MutableLiveData
import com.kay.appdb.coin.CoinItemViewModel
import com.kay.coin.model.repositories.CoinRepository
import com.kay.core.simple.SimpleDataModel
import com.kay.core.utils.switchMap
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinListViewModel @Inject constructor(private val repository: CoinRepository) : SimpleDataModel<List<CoinItemViewModel>>() {

    private val trigger = MutableLiveData<String>()


    fun refresh() {
        setRefreshing()
        repository.refresh()
    }


    fun retry() {
        mRetry?.let {
            it.invoke()
            setLoading()
        }
    }

    fun queryCoinsWithKeyword(name: String) {
        if (name != trigger.value) {
            trigger.value = name
            setLoading()
        }
    }

    init {
        mLiveData = trigger.switchMap { keyword ->
            if (keyword.isEmpty()) repository.getAllCoins()
            else {
                repository.getCoinsWithName(keyword!!)
                repository.filteredCoinsLiveData
            }
        }

        mStateEvent.apply {
            addSource(repository.requestStateEvent, {
                it?.let { extractState(it) }
            })
        }

        mRetryEvent.apply {
            addSource(repository.retryEvent, {
                mRetry = it
            })
        }

        repository.startSocket()

    }

    override fun onCleared() {
        repository.closeSocket()
        super.onCleared()
    }
}