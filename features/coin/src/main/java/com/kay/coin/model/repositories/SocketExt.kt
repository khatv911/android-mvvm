package com.kay.coin.model.repositories

import com.kay.appdb.coin.CoinMessage
import com.squareup.moshi.Moshi
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.socket.client.Socket


/**
 * Created by Kay Tran on 7/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */

fun Socket.createTradesStream(moshi: Moshi): Flowable<CoinMessage> {
    val adapter = moshi.adapter(CoinMessage::class.java)
    return Flowable.create<CoinMessage>({ sub ->
        on("trades", {
            val message = adapter.fromJson(it[0].toString())
            message?.let { it1 -> sub.onNext(it1) }
        })
    }, BackpressureStrategy.BUFFER)
}