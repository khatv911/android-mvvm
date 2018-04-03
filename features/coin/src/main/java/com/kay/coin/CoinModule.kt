package com.kay.coin

import android.arch.lifecycle.ViewModel
import com.kay.coin.view.CoinListViewModel
import com.kay.coin.view.CoinsFragment
import com.kay.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Module
abstract class CoinModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    abstract fun bindVM(vm: CoinListViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun provideMasterFragment(): CoinsFragment


}