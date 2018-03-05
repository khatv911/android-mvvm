package com.kay.core.di

import android.arch.lifecycle.ViewModelProvider
import com.kay.core.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by none on 3/2/18.
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