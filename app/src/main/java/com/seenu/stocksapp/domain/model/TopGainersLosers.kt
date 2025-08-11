package com.seenu.stocksapp.domain.model

data class TopGainersLosers(
    val topGainers : List<TopGainerLoser>,
    val topLosers : List<TopGainerLoser>
)
