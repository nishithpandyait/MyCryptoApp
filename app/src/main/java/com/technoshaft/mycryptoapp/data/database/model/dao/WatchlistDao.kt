package com.technoshaft.mycryptoapp.data.database.model.dao

import androidx.room.*
import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getAllWatchlistItems():Flow<List<WatchlistItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlistItem(watchlistItem: WatchlistItem)

    @Delete
    suspend fun deleteWatchlistItem(watchlistItem: WatchlistItem)
}
