package com.seenu.stocksapp.data.repository

import com.seenu.stocksapp.data.csv.csvParser
import com.seenu.stocksapp.data.local.StocksDatabase
import com.seenu.stocksapp.data.mappers.toCompanyListing
import com.seenu.stocksapp.data.remote.StocksApi
import com.seenu.stocksapp.domain.model.CompanyListing
import com.seenu.stocksapp.data.mappers.toCompanyListingEntity
import com.seenu.stocksapp.domain.repository.CompanyListingRepo
import com.seenu.stocksapp.util.ApiConstants
import com.seenu.stocksapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingRepoImpl @Inject constructor(
    val api : StocksApi,
    val db : StocksDatabase,
    val companyListingParser : csvParser<CompanyListing>
) : CompanyListingRepo {
    private val dao = db.companyListingDao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing=dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing=try {
                val response = api.getListings(ApiConstants.API_KEY)
                companyListingParser.parse(response.byteStream())
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Something went wrong."))
                null
            }
            catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Something went wrong."))
                null
            }
            remoteListing?.let {listing->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listing.map { it.toCompanyListingEntity()})
                emit(Resource.Success(data = dao.searchCompanyListing("").map{it.toCompanyListing()}))
                emit(Resource.Success(remoteListing))
                emit(Resource.Loading(false))
            }
        }
    }
}