package kay.clonedcoinio.models.repositories

import android.arch.lifecycle.MutableLiveData
import com.kay.core.utils.*
import com.squareup.moshi.Moshi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.socket.client.IO
import kay.clonedcoinio.models.Apis
import kay.clonedcoinio.models.AppDatabase
import kay.clonedcoinio.models.entities.CoinItemViewModel
import kay.clonedcoinio.utils.createTradesStream
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Retrofit
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrThrow
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinRepository @Inject constructor(api: Retrofit, appDB: AppDatabase, val moshi: Moshi) : BaseRepository() {


    private val webService = api.create(Apis::class.java)

    private val coinDao = appDB.coinDao()

//    val allCoinsLiveData = createNetworkBoundResource(
//            coinDao.getAllCoins(),
//            webService.getCoins(),
//            { coins -> coinDao.insert(coins) },
//            { coins -> coins?.isEmpty() ?: true }
//    )

    /**
     * this liveData is one-off, update from socket.io will not be reflected to the UI.
     */
    val filteredCoinsLiveData = SingleLiveEvent<List<CoinItemViewModel>>()


    private val disposable = CompositeDisposable()

    private val socket = IO.socket("https://coincap.io")


    fun getAllCoins() = createNetworkBoundResource(
            coinDao.getAllCoins(),
            webService.getCoins(),
            { coins -> coinDao.insert(coins) },
            { coins -> coins?.isEmpty() ?: true }
    )

    /**
     * @param name short name or long name of a coin
     */
    fun getCoinsWithName(name: String) = launch(UI) {
        val task = async(CommonPool) { coinDao.getCoinsWithName("%$name%") }
        filteredCoinsLiveData.postValue(task.await())
        requestStateEvent.value = RequestState.DONE()
    }


    /**
     * Start the socket and handle stream event
     */
    fun startSocket() {
        disposable += socket.createTradesStream(moshi)
                .buffer(3, TimeUnit.SECONDS, 10)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe {
                    it.sortBy { it.shortName }
                    it.reverse()
                    val distinct = it.distinctBy { it.shortName }
                    coinDao.update(distinct)
                }

        socket.connect()
    }

    fun closeSocket() {
        Timber.d("close socket")
        socket.disconnect()
        socket.off()
        disposable.dispose()
    }


    /**
     * Do refreshing
     */
    fun refresh(a: Int = 0) {
        launch(UI + withRetryExceptionHandler { refresh() }) {
            val networkResult = webService.getCoins().awaitResult()
            val coins = networkResult.getOrThrow()
            async(CommonPool + exceptionThrower) { coinDao.refresh(coins) }.await()
            requestStateEvent.value = RequestState.DONE()
        }
    }
}