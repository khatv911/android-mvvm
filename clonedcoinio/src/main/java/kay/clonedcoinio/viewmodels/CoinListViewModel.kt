package kay.clonedcoinio.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.kay.core.extension.smap
import com.kay.core.viewmodel.AbsBaseViewModel
import kay.clonedcoinio.models.entities.Coin
import kay.clonedcoinio.models.repositories.CoinRepository
import javax.inject.Inject

/**
 * Created by none on 10/2/18.
 */
class CoinListViewModel @Inject constructor(private val repository: CoinRepository) : AbsBaseViewModel<List<Coin>>() {

    private val trigger = MutableLiveData<Boolean>()


    fun refresh() {
        repository.refresh()
    }

    fun getAllCoins() {
        setLoading()
        trigger.value = true
    }

    fun retry() {
        repository.retry?.invoke()
    }

    init {
        mLiveData = trigger.smap { repository.getAllCoins() }
        mStateEvent.addSource(repository.state, {
            it?.let { extractStatusFromResource(it) }
        })
    }

}