package com.seenu.stocksapp.domain.repository

import com.seenu.stocksapp.domain.model.TopGainersLosers
import com.seenu.stocksapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface TopGainerLoserRepo {
    suspend fun getTopGainersLosers(fetchFromRemote: Boolean): Flow<Resource<TopGainersLosers>>
}