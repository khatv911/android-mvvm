package kay.clonedcoinio.models.repositories

import com.kay.core.livedata.BaseRepository
import com.kay.core.network.RequestState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.socket.client.IO
import kay.clonedcoinio.createTradesStream
import kay.clonedcoinio.models.Apis
import kay.clonedcoinio.models.AppDatabase
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
class CoinRepository @Inject constructor(api: Retrofit, appDB: AppDatabase) : BaseRepository() {


    private val webService = api.create(Apis::class.java)
    private val coinDao = appDB.coinDao()


    /**
     * Get all coin from db
     */
    fun getAllCoins() = createNetworkBoundResource(
            coinDao.getAllCoins(),
            webService.getCoins(),
            { coins -> coinDao.insert(coins) },
            { coins -> coins?.isEmpty() ?: true }
    )

    private val disposable = CompositeDisposable()
    private val socket = IO.socket("https://coincap.io")


    /**
     * Start the socket and handle stream event
     */
    fun startSocket() {
        disposable.add(socket.createTradesStream()
                .buffer(3, TimeUnit.SECONDS, 10)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe {
                    it.sortBy { it.shortName }
                    it.reverse()
                    val distinct = it.distinctBy { it.shortName }
                    coinDao.update(distinct)
                }
        )

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
    fun refresh() {
        launch(UI) {
            try {
                val networkResult = webService.getCoins().awaitResult()
                val coins = networkResult.getOrThrow()
                async(CommonPool) { coinDao.refresh(coins) }.await()
                state.value = RequestState.DONE()
            } catch (e: Throwable) {
                retry = { refresh() }
                state.value = RequestState.ERROR(e)
            }
        }
    }
}