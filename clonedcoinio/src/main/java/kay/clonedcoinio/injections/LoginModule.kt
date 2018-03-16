package kay.clonedcoinio.injections

import android.arch.lifecycle.ViewModel
import com.kay.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kay.clonedcoinio.viewmodels.LoginViewModel
import kay.clonedcoinio.views.LoginFragment

/**
 * Created by Kay Tran on 16/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Module
abstract class LoginModule {


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindVM(vm: LoginViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun login(): LoginFragment


}