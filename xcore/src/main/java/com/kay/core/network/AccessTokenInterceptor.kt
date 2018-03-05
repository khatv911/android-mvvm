package com.kay.core.network

/**
 * Created by KhaTran on 1/2/18.
 */
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(val pref: SharedPreferences) : Interceptor {

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