package com.kay.core.enviroments

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */

fun OkHttpClient.Builder.inject(): okhttp3.OkHttpClient.Builder {
    return this
}


fun android.app.Application.injectEnvSettings() {}