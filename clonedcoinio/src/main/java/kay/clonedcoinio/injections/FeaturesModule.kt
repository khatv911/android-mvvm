package kay.clonedcoinio.injections

import android.app.Application
import com.kay.coin.CoinModule
import dagger.Binds
import dagger.Module
import kay.clonedcoinio.App

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