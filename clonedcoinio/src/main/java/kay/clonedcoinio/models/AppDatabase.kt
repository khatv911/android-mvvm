package kay.clonedcoinio.models

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kay.clonedcoinio.models.dao.CoinDao
import kay.clonedcoinio.models.entities.Coin

/**
 * Created by none on 10/2/18.
 */
@Database(entities = [Coin::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}