package com.seenu.stocksapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopGainersLosersResponse(
    @field:Json(name="top_gainers")val topGainers : List<StockChange> = emptyList(),
    @field:Json(name="top_losers")val topLosers : List<StockChange> = emptyList()
)
@JsonClass(generateAdapter = true)
data class StockChange(
    val ticker: String,
    val price: String,
    @field:Json(name="change_amount")val changeAmount: String,
    @field:Json(name="change_percentage")val changePercentage: String,
    val volume: String
)
