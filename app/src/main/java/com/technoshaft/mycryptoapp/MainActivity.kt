package com.technoshaft.mycryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.technoshaft.mycryptoapp.data.api.model.Crypto
import com.technoshaft.mycryptoapp.data.database.model.model.WatchlistItem
import com.technoshaft.mycryptoapp.domain.viewmodel.CryptoViewModel
import com.technoshaft.mycryptoapp.domain.viewmodel.WatchlistViewModel
import com.technoshaft.mycryptoapp.presentation.ui_state.*
import com.technoshaft.mycryptoapp.ui.theme.MyCryptoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeViewModel: CryptoViewModel by viewModels()
        lateinit var watchListViewModel: WatchlistViewModel

        lifecycleScope.launch(Dispatchers.IO) {
            watchListViewModel =
                ViewModelProvider(this@MainActivity).get(WatchlistViewModel::class.java)
        }

        setContent {
            val navController = rememberNavController()


            MyCryptoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(

                        bottomBar = {
                            BottomNavigation(
                                modifier = Modifier.navigationBarsPadding(),
                            ) {
                                getBottomNavigation(navController)
                            }
                        },
                        drawerContent = {
                            // define Drawer Content here
                        },
                        drawerBackgroundColor = Color.White,
                        drawerContentColor = Color.Black,
                        drawerGesturesEnabled = true,
                        drawerScrimColor = Color.Transparent,
                        drawerShape = RoundedCornerShape(16.dp),
                        drawerElevation = 16.dp,
                        topBar = {
                            // define Top Bar here
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            // define Screen content here
                            NavHost(
                                navController = navController,
                                startDestination = "Home"
                            ) {
                                composable("Home") {
                                    CoinListScreen(viewModel = homeViewModel) { crypto: Crypto, action: Int ->
                                        when (action) {
                                            action_wishlist -> {
                                                watchListViewModel.insertWatchlistItem(
                                                    WatchlistItem.fromCrypto(
                                                        crypto
                                                    )
                                                )
                                            }
                                            action_detail -> {
                                                ChartActivity.startActivity(
                                                    this@MainActivity,
                                                    crypto
                                                )
                                            }
                                            else -> {}
                                        }

                                    }
                                }
                                composable("Profile") {// ProfileScreen(navController)

                                }
                                composable("Watchlist") {// ProfileScreen(navController)
                                    WatchlistScreen(watchlistViewModel = watchListViewModel) { coin: WatchlistItem, action: Int ->
                                        when (action) {
                                            action_wishlist -> {
                                                watchListViewModel.deleteItemFromWatchlist(coin)
                                            }
                                            action_detail -> {
                                                ChartActivity.startActivity(this@MainActivity, coin)
                                            }
                                            else -> {}
                                        }

                                    }
                                }
                                composable("Portfolio") {// ProfileScreen(navController)
                                }
                                composable("Settings") { //SettingsScreen(navController)
                                }
                            }
                        }
                    }


                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}





