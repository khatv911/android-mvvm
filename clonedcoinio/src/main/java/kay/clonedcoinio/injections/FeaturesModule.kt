package com.kay.apptemplate.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.kay.core.di.PerActivity
import com.kay.core.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import kay.clonedcoinio.App
import kay.clonedcoinio.injections.CoinModule
import javax.inject.Singleton

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Module(includes = [CoinModule::class])
abstract class FeaturesModule {

    @Binds
    abstract fun application(app: App): Application

    @Binds
    @Singleton
    abstract fun perActivityFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}