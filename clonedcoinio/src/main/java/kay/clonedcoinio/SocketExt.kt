package kay.clonedcoinio

import com.squareup.moshi.Moshi
import io.socket.client.Socket
import io.reactivex.Flowable
import io.reactivex.BackpressureStrategy
import kay.clonedcoinio.models.entities.CoinMessage


/**
 * Created by Kay Tran on 7/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */

fun Socket.createStream(): Flowable<CoinMessage> {
    val moshi: Moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(CoinMessage::class.java)
    return Flowable.create<CoinMessage>({ sub ->
        on("trades", {
            val message = adapter.fromJson(it[0].toString())
            sub.onNext(message)
        })
    }, BackpressureStrategy.BUFFER)
}