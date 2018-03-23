package kay.clonedcoinio.modules.coin

import android.arch.lifecycle.MutableLiveData
import com.kay.core.simple.SimpleDataModel
import com.kay.core.utils.switchMap
import kay.clonedcoinio.models.entities.CoinItemViewModel
import kay.clonedcoinio.models.repositories.CoinRepository
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinListViewModel @Inject constructor(private val repository: CoinRepository) : SimpleDataModel<List<CoinItemViewModel>>() {

    private val trigger = MutableLiveData<String?>()


    fun refresh() {
        setRefreshing()
        repository.refresh()
    }

    fun getAllCoins() {
        setLoading()
        trigger.value = null
    }

    fun retry() {
        mRetry?.let {
            it.invoke()
            setLoading()
        }
    }

    fun searchCoinsWithName(name: String) {
        if (name != trigger.value) {
            repository.getCoinsWithName(name)
            trigger.value = name
        }
    }

    init {
        mLiveData = trigger.switchMap { keyword ->
            if (keyword.isNullOrBlank()) repository.allCoinsLiveData
            else repository.filteredCoinsLiveData
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