package com.kay.core.di

import android.arch.lifecycle.ViewModelProvider
import com.kay.core.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Module
abstract class BaseVMModule {

    /**
     * Each And ever
     */
    @Binds
    @PerActivity
    abstract fun perActivityFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}