package kay.clonedcoinio.injections

import android.app.Application
import android.arch.persistence.room.Room
import com.kay.core.di.CoreModule
import dagger.Module
import dagger.Provides
import kay.clonedcoinio.models.AppDatabase
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by none on 2/2/18.
 */
@Module(includes = [CoreModule::class])
class AppModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl() = "http://coincap.io"

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room.databaseBuilder(application, AppDatabase::class.java, "AppDB.db").build();
}