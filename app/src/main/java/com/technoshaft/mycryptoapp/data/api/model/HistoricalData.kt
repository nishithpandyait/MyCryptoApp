package com.technoshaft.mycryptoapp.data.api.model

data class HistoricalData(
    val prices: List<List<Float>>,
    val marketCaps: List<List<Float>>,
    val totalVolumes: List<List<Float>>
)