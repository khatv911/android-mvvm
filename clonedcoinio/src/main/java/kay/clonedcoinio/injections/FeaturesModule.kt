package com.kay.apptemplate.di

import android.app.Application
import dagger.Binds
import dagger.Module
import kay.clonedcoinio.App
import kay.clonedcoinio.injections.CoinModule

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Module(includes = [CoinModule::class])
abstract class FeaturesModule {

    @Binds
    abstract fun application(app: App): Application

}