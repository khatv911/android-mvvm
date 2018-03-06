package kay.clonedcoinio

import com.kay.apptemplate.di.FeaturesModule
import com.kay.core.enviroments.injectEnvSettings
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import kay.clonedcoinio.injections.AppModule
import kay.clonedcoinio.injections.ServiceModule
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class App : dagger.android.support.DaggerApplication() {

    @Singleton
    @dagger.Component(modules = [AndroidSupportInjectionModule::class,
        AppModule::class, FeaturesModule::class, ServiceModule::class
    ]
    )
    interface Component : AndroidInjector<App> {
        @dagger.Component.Builder
        abstract class Builder : AndroidInjector.Builder<App>()
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApp_Component.builder().create(this)
    }


    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        injectEnvSettings()

    }
}