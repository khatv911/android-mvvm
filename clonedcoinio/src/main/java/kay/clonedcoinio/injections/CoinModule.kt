package kay.clonedcoinio.injections

import android.arch.lifecycle.ViewModel
import com.kay.core.di.BaseVMModule
import com.kay.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kay.clonedcoinio.viewmodels.CoinListViewModel
import kay.clonedcoinio.views.CoinListFragment

/**
 * Created by none on 25/2/18.
 */
@Module
abstract class CoinModule : BaseVMModule() {

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    abstract fun bindVM(vm: CoinListViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun provideMasterFragment(): CoinListFragment
}