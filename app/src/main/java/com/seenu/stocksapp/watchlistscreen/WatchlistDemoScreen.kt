package com.seenu.stocksapp.watchlistscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seenu.stocksapp.companylistingscreen.CompanyItem
import com.seenu.stocksapp.domain.model.CompanyListing

@Composable
fun WatchlistDemoScreen() {
    // Use a mutableStateList for watchlists so you can add/remove items
    val watchlists = remember { mutableStateListOf<String>().apply { addAll(List(10) { "Watchlist "+(it + 1) }) } }
    val dummyStocks = List(5) { j ->
        CompanyListing(
            symbol = "SYM$j",
            name = "Company $j",
            exchange = "NYSE"
        )
    }
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    // Dialog state
    var showDialog by remember { mutableStateOf(false) }
    var newWatchlistName by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(
                text = "This is a dummy screen for watchlists with expandable stocks.",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(watchlists) { index, watchlist ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.2f)
                                .clickable {
                                    expandedIndex = if (expandedIndex == index) null else index
                                },
                            tonalElevation = 8.dp,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = watchlist,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                                IconButton(onClick = { watchlists.removeAt(index) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                                }
                            }
                        }
                        if (expandedIndex == index) {
                            Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                dummyStocks.forEach { stock ->
                                    CompanyItem(
                                        company = stock,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    )
                                    HorizontalDivider(modifier = Modifier.height(2.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

        // FloatingActionButton to add a new watchlist
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Watchlist", tint = Color.White)
        }

        // AlertDialog for adding a new watchlist
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Watchlist") },
                text = {
                    OutlinedTextField(
                        value = newWatchlistName,
                        onValueChange = { newWatchlistName = it },
                        label = { Text("Watchlist Name") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newWatchlistName.isNotBlank()) {
                                watchlists.add(newWatchlistName)
                                newWatchlistName = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
} 