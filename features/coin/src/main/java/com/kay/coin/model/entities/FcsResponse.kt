package com.kay.coin.model.entities

import com.squareup.moshi.Json

/**
 * Created by Kay Tran on 16/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
data class FcsResponse<T>(
        @Json(name = "data")
        var data: T? = null,
        @Json(name = "code")
        var code: Int? = null,
        @Json(name = "endIdx")
        var endIdx: Int? = null,
        @Json(name = "max")
        var max: Int? = null,
        @Json(name = "msg")
        var msg: String? = null,
        @Json(name = "offset")
        var offset: Int? = null,
        @Json(name = "startIdx")
        var startIdx: Int? = null,
        @Json(name = "totalCount")
        var totalCount: Int? = null
)
