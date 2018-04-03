package com.kay.appdb.coin

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Entity(tableName = "tbl_coin")
data class Coin(
        @PrimaryKey(autoGenerate = true)
        var id: Long=0L,
        var cap24hrChange: Double = 0.0,
        @Json(name = "long")
        var longName: String,
        var mktcap: String,
        var perc: Double = 0.0,
        var price: Double = 0.0,
        var shapeshift: Boolean = false,
        @Json(name = "short")
        var shortName: String,
        var supply: Double = 0.0,
        var usdVolume: Double = 0.0,
        var volume: Double = 0.0,
        var vwapData: String = "",
        var vwapDataBTC: String=""
)