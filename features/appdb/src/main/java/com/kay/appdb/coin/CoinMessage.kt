package com.kay.appdb.coin

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by Kay Tran on 6/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
@JsonClass(generateAdapter = true)
data class CoinMessage(
        @Json(name = "coin")
        var shortName: String?,
        @Json(name = "msg")
        var coin: Coin?
)