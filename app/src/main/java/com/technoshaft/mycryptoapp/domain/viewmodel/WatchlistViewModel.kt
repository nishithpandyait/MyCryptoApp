package com.technoshaft.mycryptoapp.domain.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.*
import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem
import com.technoshaft.mycryptoapp.data.repo.WatchlistRepository
import com.technoshaft.mycryptoapp.presentation.ui_state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val repository: WatchlistRepository) :
    ViewModel() {

    private val _watchlist = MutableLiveData<Resource<List<WatchlistItem>>>(Resource.Loading())
    val watchlist: LiveData<Resource<List<WatchlistItem>>> = _watchlist

    init {
        loadWatchlist()
    }

    fun loadWatchlist() {

        viewModelScope.launch {
            try {
                val data = repository.allWatchlistItems
                data.collect() {
                    _watchlist.value = Resource.Success(it)
                }

            } catch (e: Exception) {
                _watchlist.value = Resource.Error(e.message ?: "Unknown error", null)
            }
        }
    }

    fun insertWatchlistItem(item: WatchlistItem) {
        viewModelScope.launch {
            repository.insertWatchlistItem(item)
        }
    }

    fun deleteItemFromWatchlist(item: WatchlistItem) {
        viewModelScope.launch {
            repository.deleteWatchlistItem(item)
            loadWatchlist()
        }
    }
}
