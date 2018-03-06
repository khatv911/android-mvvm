package kay.clonedcoinio.injections

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import kay.clonedcoinio.CustomListener
import kay.clonedcoinio.SocketIoService
import kay.clonedcoinio.models.AppDatabase


/**
 * Created by Kay Tran on 6/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun provideSerice(): SocketIoService

}





