package com.seenu.stocksapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyOverviewResponse(
    @field:Json(name = "Symbol") val ticker : String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "MarketCapitalization") val marketCap: String?,
    @field:Json(name = "PERatio") val peRatio: String?,
    @field:Json(name = "ProfitMargin") val profitMargin: String?,
    @field:Json(name = "Sector") val sector: String?,
    @field:Json(name = "Industry") val industry: String?,
    @field:Json(name = "52WeekHigh") val weekHigh52: String?,
    @field:Json(name = "52WeekLow") val weekLow52: String?,
    @field:Json(name = "Note") val note: String? = null,
    @field:Json(name = "Information") val information: String? = null,
    @field:Json(name = "Error Message") val errorMessage: String? = null
) 