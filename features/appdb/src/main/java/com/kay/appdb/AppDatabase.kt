package com.kay.appdb

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.kay.appdb.coin.Coin
import com.kay.appdb.coin.CoinDao

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
@Database(entities = [Coin::class], exportSchema = true, version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun coinDao(): CoinDao
}