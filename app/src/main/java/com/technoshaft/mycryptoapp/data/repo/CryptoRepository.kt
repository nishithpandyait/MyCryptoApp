package com.technoshaft.mycryptoapp.data.repo

import com.technoshaft.mycryptoapp.data.api.model.Crypto
import com.technoshaft.mycryptoapp.data.api.CoinGeckoApi
import com.technoshaft.mycryptoapp.data.api.model.HistoricalData
import com.technoshaft.mycryptoapp.presentation.ui_state.Resource
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val api: CoinGeckoApi) {

    suspend fun getCryptos(): Resource<List<Crypto>> {
        return try {
            val cryptos = api.getCryptos()
            Resource.Success(cryptos)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getHistoricalData(
        id: String,
        currency: String,
        days: Int,
    ): Resource<HistoricalData> {
        return try {
            val cryptos = api.getHistoricalData(id, currency, days)
            if (cryptos.isSuccessful)
                Resource.Success(cryptos.body())
            else
                Resource.Error("An error occurred")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}