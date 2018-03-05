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
interface CoinDao {

    @Query("select * from tbl_coin")
    fun getAllCoins(): LiveData<List<Coin>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lst: List<Coin>)


}