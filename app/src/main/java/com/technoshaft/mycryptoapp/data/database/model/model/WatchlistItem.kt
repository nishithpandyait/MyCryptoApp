package com.technoshaft.mycryptoapp.data.database.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.technoshaft.mycryptoapp.data.api.model.Crypto
import java.util.Objects

@Entity(tableName = "watchlist")
data class WatchlistItem(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String?,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,
    val current_price: String



){
    companion object{
        fun fromCrypto(crypto: Crypto): WatchlistItem {
            return WatchlistItem(
                crypto.id,
                crypto.name,
                crypto.symbol,
                crypto.image,
                crypto.current_price,
                crypto.current_price,
                crypto.current_price.toString()
            )
        }
    }
}
