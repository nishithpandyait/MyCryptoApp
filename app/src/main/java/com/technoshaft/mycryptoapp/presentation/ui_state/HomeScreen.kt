package com.technoshaft.mycryptoapp.presentation.ui_state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.technoshaft.mycryptoapp.data.api.model.Crypto
import com.technoshaft.mycryptoapp.domain.viewmodel.CryptoViewModel

const val action_detail: Int = 1
const val action_wishlist: Int = 2

@Composable
fun CryptoCard(coin: Crypto, function: (crypto: Crypto, action: Int) -> Unit) {

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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinListScreen(viewModel: CryptoViewModel, function: (crypto: Crypto, action: Int) -> Unit) {
    val coinsState by viewModel.cryptos.observeAsState(Resource.Loading())
    var state: Boolean by remember { mutableStateOf(true) }
    val rememberPullRefreshState =
        rememberPullRefreshState(refreshing = state, onRefresh = { /*TODO*/ })
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state)
            PullRefreshIndicator(refreshing = state, state = rememberPullRefreshState)
        when (coinsState) {
            is Resource.Loading<*> -> {
                // show loading state
                state = true
            }
            is Resource.Error<*> -> {
                val error = (coinsState as Resource.Error).message
                // show error state with error message
                state = false
            }
            is Resource.Success<List<Crypto>> -> {
                // show success state with list of coins
                state = false
                val coins = (coinsState as Resource.Success<List<Crypto>>).data
                coins?.let { cryptos ->

                    LazyColumn {
                        items(cryptos) { crypto ->
                            CryptoCard(coin = crypto, function)
                        }
                    }

                }
            }
        }
    }


    // Call getAllCoins function to fetch the coins
    LaunchedEffect(Unit) {
        state = true
        viewModel.getAllCrypto()
    }
}