package com.technoshaft.mycryptoapp.domain.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoshaft.mycryptoapp.data.api.model.Crypto
import com.technoshaft.mycryptoapp.data.repo.CryptoRepository
import com.technoshaft.mycryptoapp.presentation.ui_state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptos =
        MutableLiveData<Resource<List<Crypto>>>(Resource.Loading())
    val cryptos: LiveData<Resource<List<Crypto>>> = _cryptos



    fun getAllCrypto() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getCryptos()
            _cryptos.value = result
        }
    }
}