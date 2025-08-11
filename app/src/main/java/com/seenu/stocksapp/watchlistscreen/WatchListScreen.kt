package com.seenu.stocksapp.watchlistscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.seenu.stocksapp.BottomNavigationItem
import com.seenu.stocksapp.util.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListScreen(
    navController: NavController,
    viewModel: WatchlistViewModel= hiltViewModel()
) {
    val items = listOf<BottomNavigationItem>(
        BottomNavigationItem(
            "Home",
            Icons.Filled.Home,
            Icons.Default.Home,
            NavigationRoutes.companyListingScreen
        ),
        BottomNavigationItem(
            "Watchlist",
            Icons.Filled.Favorite,
            Icons.Default.FavoriteBorder,
            NavigationRoutes.watchListScreen),
        BottomNavigationItem(
            "Top Gainers/Losers",
            Icons.Filled.Star,
            Icons.Filled.Star,
            NavigationRoutes.topGainersLosersScreen)
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { TopAppBar(title = { Text("StocksApp") }) },
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        },
    ) {
            Column(
                modifier = Modifier.padding(it)
            ){
                WatchlistDemoScreen()
            }
    }



}