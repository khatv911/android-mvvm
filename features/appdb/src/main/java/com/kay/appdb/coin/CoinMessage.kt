package com.kay.appdb.coin

import com.squareup.moshi.Json


/**
 * Created by Kay Tran on 6/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
data class CoinMessage(
        @Json(name = "coin")
        var shortName: String?,
        @Json(name = "msg")
        var coin: Coin?
)