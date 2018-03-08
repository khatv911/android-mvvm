package kay.clonedcoinio

import com.squareup.moshi.Moshi
import io.socket.emitter.Emitter
import kay.clonedcoinio.models.AppDatabase
import kay.clonedcoinio.models.entities.CoinMessage
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Kay Tran on 6/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class CustomListener constructor(appDatabase: AppDatabase) : Emitter.Listener {

//    private val dao = appDatabase.coinDao()
    private val moshi: Moshi = Moshi.Builder().build()
    private val adapter = moshi.adapter(CoinMessage::class.java)
    override fun call(vararg args: Any?) {
        val message = adapter.fromJson(args[0].toString())
//        dao.update(message.shortName!!, message.coin?.price ?: 0.0)
    }


}