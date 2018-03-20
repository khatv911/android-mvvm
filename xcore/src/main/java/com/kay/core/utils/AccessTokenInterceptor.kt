package com.kay.core.utils

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val pref: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .apply {
                    val accessToken = pref.getString("access_token", null)
                    accessToken?.let {
                        addHeader("Authorization", "Bearer ${pref.getString("access_token", "")}")
                    }
                }.build()
        return chain.proceed(request)
    }
}