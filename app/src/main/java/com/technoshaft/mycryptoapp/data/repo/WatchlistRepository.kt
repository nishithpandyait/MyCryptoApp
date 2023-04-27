package com.technoshaft.mycryptoapp.data.repo

import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem
import com.technoshaft.mycryptoapp.data.database.model.dao.WatchlistDao
import kotlinx.coroutines.flow.Flow

class WatchlistRepository(private val watchlistDao: WatchlistDao) {
    val allWatchlistItems: Flow<List<WatchlistItem>> = watchlistDao.getAllWatchlistItems()

    suspend fun insertWatchlistItem(watchlistItem: WatchlistItem) {
        watchlistDao.insertWatchlistItem(watchlistItem)
    }

    suspend fun deleteWatchlistItem(watchlistItem: WatchlistItem) {
        watchlistDao.deleteWatchlistItem(watchlistItem)
    }
}
