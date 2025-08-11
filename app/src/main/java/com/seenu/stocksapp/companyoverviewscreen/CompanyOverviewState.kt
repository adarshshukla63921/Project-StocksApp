package com.seenu.stocksapp.companyoverviewscreen

import com.seenu.stocksapp.domain.model.CompanyOverview
import com.seenu.stocksapp.domain.model.IntradayInfo

data class CompanyOverviewState(
    val stockInfo : List<IntradayInfo> = emptyList(),
    val company : CompanyOverview? = null,
    val isLoading : Boolean = false,
    val error : String? = null,
    val isRefreshing : Boolean=false
)
