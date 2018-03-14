package kay.clonedcoinio.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.kay.core.extension.smap
import com.kay.core.simple.SimpleViewModel
import kay.clonedcoinio.models.entities.Coin
import kay.clonedcoinio.models.repositories.CoinRepository
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinListViewModel @Inject constructor(private val repository: CoinRepository) : SimpleViewModel<List<Coin>>() {

    private val trigger = MutableLiveData<Boolean>()


    fun refresh() {
        setRefreshing()
        repository.refresh()
    }

    fun getAllCoins() {
        setLoading()
        trigger.value = true
    }

    fun retry() {
        setLoading()
        repository.retry?.invoke()
    }

    init {
        mLiveData = trigger.smap { repository.getAllCoins() }
        mStateEvent.addSource(repository.state, {
            it?.let { extractState(it) }
        })
        repository.startSocket()

    }

    override fun onCleared() {
        repository.closeSocket()
        super.onCleared()
    }
}