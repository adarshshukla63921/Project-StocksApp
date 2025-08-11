package com.seenu.stocksapp.domain.repository

import com.seenu.stocksapp.domain.model.CompanyOverview
import com.seenu.stocksapp.util.Resource

interface CompanyOverviewRepo {
    suspend fun getCompanyOverview(symbol : String, fetchFromRemote : Boolean=false) : Resource<CompanyOverview>
}