package com.kay.core.enviroments

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by none on 10/2/18.
 */

fun OkHttpClient.Builder.inject(): okhttp3.OkHttpClient.Builder {
    return this
}


fun android.app.Application.injectEnvSettings() {}