package kay.clonedcoinio.models.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kay.clonedcoinio.models.entities.Coin

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Dao
abstract class CoinDao {

    @Query("select * from tbl_coin")
    abstract fun getAllCoins(): LiveData<List<Coin>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(lst: List<Coin>)

    @Query("delete from tbl_coin")
    abstract fun deleteAllCoins()

    @Transaction
    open fun refresh(coins: List<Coin>) {
        deleteAllCoins()
        insert(coins)
    }

}