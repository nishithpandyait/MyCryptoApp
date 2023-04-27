package com.technoshaft.mycryptoapp.domain.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoshaft.mycryptoapp.data.api.model.HistoricalData
import com.technoshaft.mycryptoapp.data.api.model.Timespan
import com.technoshaft.mycryptoapp.data.repo.CryptoRepository
import com.technoshaft.mycryptoapp.presentation.ui_state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {


    private val _historicalData = MutableLiveData<Resource<HistoricalData>>(Resource.Loading())
    val historicalData: LiveData<Resource<HistoricalData>> = _historicalData

    fun loadHistoricalData(id: String, currency: String, days: Int) {

        viewModelScope.launch {

            val data = repository.getHistoricalData(id, currency, days)

            _historicalData.value = data
        }
    }

    fun bindToBitcoinPrice(timespan: Timespan) {

    }
}