package com.technoshaft.mycryptoapp.data.api.model

data class Crypto(
    val id: String,
    val symbol: String,
    val name: String,
    val current_price: Double,
    val image: String,
    val market_cap_rank: Int
)
