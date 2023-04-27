package com.technoshaft.mycryptoapp.presentation.ui_state

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun getBottomNavigation(navController: NavController) {
    BottomNavigation(
        modifier = Modifier.navigationBarsPadding(),
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "Home",
            onClick = {
                navController.navigate("Home") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = navController.currentDestination?.route == "Profile",
            onClick = {
                navController.navigate("Profile") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.MailOutline, contentDescription = null) },
            label = { Text("Watchlist") },
            selected = navController.currentDestination?.route == "Watchlist",
            onClick = {
                navController.navigate("Watchlist") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            label = { Text("Portfolio") },
            selected = navController.currentDestination?.route == "Portfolio",
            onClick = {
                navController.navigate("Portfolio") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = navController.currentDestination?.route == "Settings",
            onClick = {
                navController.navigate("Settings") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        // add more items here
    }

}