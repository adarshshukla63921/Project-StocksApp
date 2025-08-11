package com.seenu.stocksapp.data.repository

import com.seenu.stocksapp.data.csv.csvParser
import com.seenu.stocksapp.data.local.StocksDatabase
import com.seenu.stocksapp.data.remote.StocksApi
import com.seenu.stocksapp.domain.model.IntradayInfo
import com.seenu.stocksapp.domain.repository.IntradayInfoRepo
import com.seenu.stocksapp.util.ApiConstants
import com.seenu.stocksapp.util.Resource
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoRepoImpl @Inject constructor(
    private val api : StocksApi,
    private val intradayInfoParser : csvParser<IntradayInfo>
) : IntradayInfoRepo {
    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol = symbol, apikey = ApiConstants.API_KEY)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)

        }catch (e : IOException){
            e.printStackTrace()
            Resource.Error(message = "IOException Encountered",null)

        }catch (e : HttpException){
            e.printStackTrace()
            Resource.Error(message = "HttpException Encountered",null)
        }
    }
}