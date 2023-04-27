package com.technoshaft.mycryptoapp.data.api

import com.technoshaft.mycryptoapp.data.api.model.Crypto
import com.technoshaft.mycryptoapp.data.api.model.HistoricalData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {


    @GET("coins/markets")
    suspend fun getCryptos(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") limit: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
    ): List<Crypto>


    @GET("coins/{id}/market_chart")
    suspend fun getHistoricalData(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: Int,
    ): Response<HistoricalData>



}