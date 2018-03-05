package kay.clonedcoinio.models.repositories

import com.kay.core.livedata.BaseRepository
import com.kay.core.network.RequestState
import kay.clonedcoinio.models.Apis
import kay.clonedcoinio.models.AppDatabase
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Retrofit
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrThrow
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


    /**
     * Do refreshing
     */
    fun refresh() {
        launch(UI) {
            try {
                val networkResult = webService.getCoins().awaitResult()
                val coins = networkResult.getOrThrow()
                async(CommonPool) { coinDao.insert(coins) }.await()
                state.value = RequestState.SUCCESS("data updated")
            } catch (e: Throwable) {
                retry = { refresh() }
                state.value = RequestState.ERROR(e)
            }
        }
    }
}