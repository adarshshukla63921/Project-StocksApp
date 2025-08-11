package com.seenu.stocksapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seenu.stocksapp.companylistingscreen.CompanyListingScreen
import com.seenu.stocksapp.companyoverviewscreen.CompanyDetailsScreen
import com.seenu.stocksapp.topgainerslosersscreen.TopGainersLosersScreen
import com.seenu.stocksapp.util.NavigationRoutes
import com.seenu.stocksapp.watchlistscreen.WatchListScreen


@Composable
fun rootApp(){
    Surface {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = NavigationRoutes.companyListingScreen){
            composable(NavigationRoutes.companyListingScreen){
                CompanyListingScreen(navController)
            }
            composable(NavigationRoutes.watchListScreen){
                WatchListScreen(navController)
            }
            composable(NavigationRoutes.topGainersLosersScreen){
                TopGainersLosersScreen(navController)
            }
            composable("company_overview/{ticker}"){
                CompanyDetailsScreen(navController, ticker = it.arguments?.getString("ticker")?:"")
            }
        }
    }
}