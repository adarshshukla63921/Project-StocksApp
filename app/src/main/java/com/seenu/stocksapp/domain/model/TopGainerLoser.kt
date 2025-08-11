package com.seenu.stocksapp.domain.model

data class TopGainerLoser(
    val ticker: String,
    val price: String,
    val changeAmount: String,
    val changePercentage: String
)
