package com.seenu.stocksapp.topgainerslosersscreen

import com.seenu.stocksapp.domain.model.TopGainerLoser

data class TopGainersLosersState(
    val topGainers: List<TopGainerLoser> = emptyList(),
    val topLosers: List<TopGainerLoser> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewAllType: ViewAllType? = null,
    val isRefreshing: Boolean = false
)
enum class ViewAllType {
    GAINERS, LOSERS
}