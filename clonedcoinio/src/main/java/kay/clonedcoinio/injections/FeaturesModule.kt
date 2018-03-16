package com.kay.apptemplate.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.kay.core.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import kay.clonedcoinio.App
import kay.clonedcoinio.modules.coin.CoinModule
import kay.clonedcoinio.modules.login.LoginModule
import javax.inject.Singleton

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Module(includes = [CoinModule::class, LoginModule::class])
abstract class FeaturesModule {

    @Binds
    abstract fun application(app: App): Application

    @Binds
    @Singleton
    abstract fun perActivityFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}