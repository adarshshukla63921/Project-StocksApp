package com.seenu.stocksapp.topgainerslosersscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.seenu.stocksapp.BottomNavigationItem
import com.seenu.stocksapp.util.NavigationRoutes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopGainersLosersScreen(
    navController: NavController,
    viewModel: TopGainersLosersViewmodel = hiltViewModel()
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
    val state by viewModel.state.collectAsState()
    val viewAllType = remember { mutableStateOf<ViewAllType?>(null) }
    val topGainers = state.topGainers.take(4).chunked(2)
    val topLosers = state.topLosers.take(4).chunked(2)
    val swipeRefreshState= rememberSwipeRefreshState(state.isRefreshing)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {viewModel.onRefresh()}
    ) {
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
        ) {
            Column(
                modifier = Modifier.padding(it)
            ){
                if (viewAllType.value == null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Top Gainers",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)
                        )
                        Text(
                            text = "view all",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 5.dp).clickable{viewAllType.value = ViewAllType.GAINERS}
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                    ){
                        topGainers.forEach { rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { gainer ->
                                    TopGainerLoserCard(
                                        item = gainer,
                                        isGainer = true,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(120.dp),
                                        onClick = {
                                            if (gainer.ticker.isNotBlank()) {
                                                navController.navigate("company_overview/${gainer.ticker}")
                                            }
                                        }
                                    )
                                }

                                if (rowItems.size < 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Top Losers",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)
                        )
                        Text(
                            text = "view all",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 5.dp).clickable{viewAllType.value = ViewAllType.LOSERS}
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                    ){
                        topLosers.forEach { rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { gainer ->
                                    TopGainerLoserCard(
                                        item = gainer,
                                        isGainer = false,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(120.dp),
                                        onClick = {
                                            if (gainer.ticker.isNotBlank()) {
                                                navController.navigate("company_overview/${gainer.ticker}")
                                            }
                                        }
                                    )
                                }
                                if (rowItems.size < 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                } else {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = if (viewAllType.value == ViewAllType.GAINERS) "All Gainers" else "All Losers",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    text = "view less",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(horizontal = 5.dp).clickable{viewAllType.value = null}
                                )
                            }
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val list = if (viewAllType.value == ViewAllType.GAINERS) state.topGainers else state.topLosers
                                items(list.size) { idx ->
                                    TopGainerLoserCard(
                                        item = list[idx],
                                        isGainer = viewAllType.value == ViewAllType.GAINERS,
                                        onClick = {
                                            if (list[idx].ticker.isNotBlank()) {
                                                navController.navigate("company_overview/${list[idx].ticker}")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}