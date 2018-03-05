package com.kay.apptemplate.di

import android.app.Application
import dagger.Binds
import dagger.Module
import kay.clonedcoinio.App
import kay.clonedcoinio.injections.CoinModule

/**
 * Created by KhaTran on 2/2/18.
 */
@Module(includes = [CoinModule::class])
abstract class FeaturesModule {

    @Binds
    abstract fun application(app: App): Application

}