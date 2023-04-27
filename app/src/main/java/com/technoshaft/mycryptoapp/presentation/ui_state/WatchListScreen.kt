package com.technoshaft.mycryptoapp.presentation.ui_state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem
import com.technoshaft.mycryptoapp.domain.viewmodel.WatchlistViewModel

@Composable
fun WatchListItemCard(coin: WatchlistItem, function: (coin: WatchlistItem, action: Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { function(coin, action_detail) },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.clickable { function.invoke(coin, action_wishlist) }
            )
            Text(
                text = coin.name,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = coin.symbol,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "$${coin.current_price}",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WatchlistScreen(
    watchlistViewModel: WatchlistViewModel,
    function: (coin: WatchlistItem, action: Int) -> Unit,
) {
    val watchlistItemsState = watchlistViewModel.watchlist.observeAsState()

    when (watchlistItemsState.value) {
        is Resource.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error ")
            }
        }
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items((watchlistItemsState.value as Resource.Success<List<WatchlistItem>>).data!!) { watchlistItem ->
                    WatchListItemCard(watchlistItem, function)
                }
            }
        }
        else -> {}
    }
}