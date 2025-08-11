package com.seenu.stocksapp.domain.model

import com.squareup.moshi.Json

data class CompanyOverview(
        val ticker : String,
        val name: String?,
        val description: String?,
        val marketCap: String?,
        val peRatio: String?,
        val profitMargin: String?,
        val sector: String?,
        val industry: String?,
        val weekHigh52: String?,
        val weekLow52: String?
)
