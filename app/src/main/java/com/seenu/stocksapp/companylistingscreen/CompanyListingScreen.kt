package com.seenu.stocksapp.companylistingscreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.seenu.stocksapp.BottomNavigationItem
import com.seenu.stocksapp.util.NavigationRoutes
import androidx.compose.material3.Icon
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingScreen(
    navController: NavController,
    viewModel: CompanyListingViewModel = hiltViewModel()
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
    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = {
                        viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(it))
                    },
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    placeholder = { Text(text = "Search") },
                    maxLines = 1,
                    shape = RoundedCornerShape(12.dp)
                )
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.onEvent(CompanyListingEvent.Refresh)
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.companies.size) {
                                idx ->
                            val company = state.companies[idx]
                            val modifier = Modifier.fillMaxWidth().clickable {
                                if (state.companies[idx].symbol.isNotBlank()) {
                                    navController.navigate("company_overview/${state.companies[idx].symbol}")
                                }
                            }.padding(10.dp)
                            CompanyItem(company, modifier)
                            if (idx < state.companies.size) {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}