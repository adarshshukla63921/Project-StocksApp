package com.seenu.stocksapp.data.remote

import com.seenu.stocksapp.data.remote.dto.CompanyOverviewResponse
import com.seenu.stocksapp.data.remote.dto.TopGainersLosersResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StocksApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String
    ) : ResponseBody

    @GET("query?function=TOP_GAINERS_LOSERS")
    suspend fun getTopGainersLosers(
        @Query("apikey") apiKey : String,
    )  : TopGainersLosersResponse

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol : String,
        @Query("apikey") apikey : String
    ) : ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyOverview(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): CompanyOverviewResponse
}