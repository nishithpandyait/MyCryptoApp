package com.technoshaft.mycryptoapp.data.database.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technoshaft.mycryptoapp.data.database.model.dao.WatchlistDao
import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem

@Database(entities = [WatchlistItem::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao


}
