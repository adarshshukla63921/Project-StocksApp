package com.seenu.stocksapp.companyoverviewscreen


import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    navController: NavController,
    ticker : String,
    viewmodel : CompanyOverviewViewmodel = hiltViewModel()
) {

    val state = viewmodel.state
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
    Scaffold(
        topBar = { TopAppBar(title = { Text("StocksApp") },
            actions = { IconButton(onClick = {
                navController.popBackStack()
            }){ Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back"
            ) } }) },
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {viewmodel.onRefresh()}
            ) {
                if (state.error == null) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
                    ) {
                        state.company?.let { company ->
                            Text(
                                text = company.name ?: "",
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = company.ticker,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Industry: ${company.industry}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Sector: ${company.sector}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                overflow = TextOverflow.Ellipsis
                            )
                            if (company.marketCap != "None") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Market Cap: $${company.marketCap}",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            if (company.peRatio != "None") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "PE Ratio :'$' ${company.marketCap}",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Profit Margin : ${company.profitMargin}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(8.dp))
                            Box{
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "In the past 52 weeks.",
                                        modifier = Modifier.padding(8.dp),
                                        style = MaterialTheme.typography.titleMedium,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Highest Price: ${company.weekHigh52}",
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier.weight(1f),
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Text(
                                            text = "Lowest Price: ${company.weekLow52}",
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier.weight(1f),
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = company.description?:"",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                            )
                            if(state.stockInfo.isNotEmpty()){
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Market Analysis")
                                Spacer(modifier = Modifier.height(8.dp))
                                StockChart(
                                    infos = state.stockInfo,
                                    modifier = Modifier.fillMaxWidth().height(250.dp).align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }else{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "An error occurred:",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            androidx.compose.material3.Button(onClick = { viewmodel.onRefresh() }) {
                                Text("Retry")
                            }
                            state.company?.let { company ->
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "Showing last available data:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}