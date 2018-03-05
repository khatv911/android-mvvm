package kay.clonedcoinio.models.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kay.clonedcoinio.models.entities.Coin

/**
 * Created by none on 10/2/18.
 */
@Dao
interface CoinDao {

    @Query("select * from tbl_coin")
    fun getAllCoins(): LiveData<List<Coin>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lst: List<Coin>)


}