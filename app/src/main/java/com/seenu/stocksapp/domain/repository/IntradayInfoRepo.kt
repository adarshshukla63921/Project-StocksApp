package com.seenu.stocksapp.domain.repository

import com.seenu.stocksapp.domain.model.IntradayInfo
import com.seenu.stocksapp.util.Resource

interface IntradayInfoRepo {
    suspend fun getIntradayInfo(symbol : String) : Resource<List<IntradayInfo>>
}