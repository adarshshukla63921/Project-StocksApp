package com.seenu.stocksapp.domain.repository

import com.seenu.stocksapp.domain.model.CompanyListing
import com.seenu.stocksapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CompanyListingRepo {
    suspend fun getCompanyListing(
        fetchFromRemote : Boolean,
        query : String
    ) : Flow<Resource<List<CompanyListing>>>
}