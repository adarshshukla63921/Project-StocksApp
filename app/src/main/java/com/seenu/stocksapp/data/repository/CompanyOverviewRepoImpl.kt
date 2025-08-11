package com.seenu.stocksapp.data.repository

import android.util.Log
import com.seenu.stocksapp.data.mappers.toDomain
import com.seenu.stocksapp.data.mappers.toEntity
import com.seenu.stocksapp.data.remote.StocksApi
import com.seenu.stocksapp.domain.model.CompanyOverview
import com.seenu.stocksapp.domain.repository.CompanyOverviewRepo
import com.seenu.stocksapp.util.ApiConstants
import com.seenu.stocksapp.util.Resource
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton
import com.seenu.stocksapp.data.local.dao.CompanyOverviewDao
import com.seenu.stocksapp.data.local.entities.CompanyOverviewEntity

@Singleton
class CompanyOverviewRepoImpl @Inject constructor(
    private val api: StocksApi,
    private val companyOverviewDao: CompanyOverviewDao
) : CompanyOverviewRepo {
    override suspend fun getCompanyOverview(
        symbol: String,
        fetchFromRemote: Boolean
    ): Resource<CompanyOverview> {
        try {
            if (!fetchFromRemote) {
                val local = companyOverviewDao.getCompanyOverview(symbol)
                if (local != null) {
                    return Resource.Success(local.toDomain())
                }
            }
            val result = api.getCompanyOverview(symbol, ApiConstants.API_KEY)
            val entity = result.toEntity()
            companyOverviewDao.upsertCompanyOverview(entity)
            return Resource.Success(entity.toDomain())
        } catch (e: IOException) {
            e.printStackTrace()
            return Resource.Error(message = "IOException Encountered", null)
        } catch (e: HttpException) {
            e.printStackTrace()
            return Resource.Error(message = "HttpException Encountered", null)
        }
    }
}