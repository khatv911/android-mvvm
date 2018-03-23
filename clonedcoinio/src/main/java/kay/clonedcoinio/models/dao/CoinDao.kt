package kay.clonedcoinio.models.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kay.clonedcoinio.models.entities.Coin
import kay.clonedcoinio.models.entities.CoinItemViewModel
import kay.clonedcoinio.models.entities.CoinMessage

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Dao
abstract class CoinDao {

    @Query("select longName, shortName, price from tbl_coin")
    abstract fun getAllCoins(): LiveData<List<CoinItemViewModel>?>

    @Query("SELECT longName, shortName, price FROM tbl_coin WHERE shortName LIKE :name OR longName LIKE :name")
    abstract fun getCoinsWithName(name: String): List<CoinItemViewModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(lst: List<Coin>)

    @Query("delete from tbl_coin")
    abstract fun deleteAllCoins()

    @Query("update tbl_coin set price= :price where shortName= :name")
    abstract fun update(name: String, price: Double)

    @Transaction
    open fun update(lst: List<CoinMessage>) {
        lst.map {
            update(it.shortName!!, it.coin?.price ?: 0.0)
        }
    }

    @Transaction
    open fun refresh(coins: List<Coin>) {
        deleteAllCoins()
        insert(coins)
    }

}