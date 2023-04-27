package com.technoshaft.mycryptoapp.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.technoshaft.mycryptoapp.data.api.CoinGeckoApi
import com.technoshaft.mycryptoapp.data.database.model.CryptoDatabase
import com.technoshaft.mycryptoapp.data.database.model.dao.WatchlistDao
import com.technoshaft.mycryptoapp.data.repo.CryptoRepository
import com.technoshaft.mycryptoapp.data.repo.WatchlistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

    @Provides
    fun provideCryptoService(): CoinGeckoApi {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoApi::class.java)
    }

    @Provides
    fun provideCryptoRepository(cryptoService: CoinGeckoApi): CryptoRepository {
        return CryptoRepository(cryptoService)
    }


    @Provides
    @Singleton
    fun provideWatchlistDatabase(@ApplicationContext appContext: Context): CryptoDatabase {
       return Room.databaseBuilder(
            appContext,
            CryptoDatabase::class.java,
            "watchlist_database"
        ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .build()

    }



    @Provides
    fun provideWatchlistDao(database: CryptoDatabase): WatchlistDao {
        return database.watchlistDao()
    }

    @Provides
    fun provideWatchlistRepository(dao: WatchlistDao): WatchlistRepository {
        return WatchlistRepository(dao)
    }

}
