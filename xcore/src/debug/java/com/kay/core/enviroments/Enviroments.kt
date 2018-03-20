package com.kay.core.enviroments

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.leakcanary.AndroidExcludedRefs
import com.squareup.leakcanary.DisplayLeakService
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.LeakCanary.refWatcher
import com.squareup.leakcanary.RefWatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber


/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */

fun OkHttpClient.Builder.inject(): okhttp3.OkHttpClient.Builder {
    return apply {
        addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.tag("OkHttp").d(message)
        }).setLevel(HttpLoggingInterceptor.Level.BODY))
        addNetworkInterceptor(StethoInterceptor())
    }
}


private fun installCustomLeakCanary(application: Application): RefWatcher {
    return refWatcher(application).listenerServiceClass(DisplayLeakService::class.java)
            .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
            .maxStoredHeapDumps(50)
            .buildAndInstall()
}


fun android.app.Application.injectEnvSettings() {

    Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build())


    // enable strict mode
    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectAll()
            .penaltyLog()
            .build())
    StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build())


    if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
        return
    }

    installCustomLeakCanary(this)
}

