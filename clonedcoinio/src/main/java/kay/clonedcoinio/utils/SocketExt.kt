package kay.clonedcoinio.utils

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.socket.client.Socket
import kay.clonedcoinio.models.entities.CoinMessage


/**
 * Created by Kay Tran on 7/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */

fun Socket.createTradesStream(): Flowable<CoinMessage> {
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter = moshi.adapter(CoinMessage::class.java)
    return Flowable.create<CoinMessage>({ sub ->
        on("trades", {
            val message = adapter.fromJson(it[0].toString())
            message?.let { it1 -> sub.onNext(it1) }
        })
    }, BackpressureStrategy.BUFFER)
}