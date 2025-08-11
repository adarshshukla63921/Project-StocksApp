package com.seenu.stocksapp.data.repository

import com.seenu.stocksapp.data.local.StocksDatabase
import com.seenu.stocksapp.data.local.entities.TopGainerLoserEntity
import com.seenu.stocksapp.data.mappers.toDomain
import com.seenu.stocksapp.data.remote.StocksApi
import com.seenu.stocksapp.domain.model.TopGainersLosers
import com.seenu.stocksapp.domain.repository.TopGainerLoserRepo
import com.seenu.stocksapp.util.ApiConstants
import com.seenu.stocksapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopGainerLoserRepoImpl @Inject constructor(
    private val api: StocksApi,
    private val db: StocksDatabase
) : TopGainerLoserRepo {
    private val dao=db.topGainersLosersDao
    override suspend fun getTopGainersLosers(fetchFromRemote: Boolean): Flow<Resource<TopGainersLosers>> {
        return flow {
            emit(Resource.Loading(true))
            val local = dao.getAll()
            val dbEmpty = local.isEmpty()
            val fetchFromCache = !dbEmpty && !fetchFromRemote

            if (fetchFromCache) {
                emit(Resource.Success(local.toDomain()))
                emit(Resource.Loading(false))
                return@flow
            }
            val apiKey = ApiConstants.API_KEY
            val response = try {
                api.getTopGainersLosers(apiKey)
            } catch (e: retrofit2.HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "HTTP error"))
                null
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "Network error"))
                null
            }
            response?.let {
                var topLosers = it.topLosers.map { loser ->
                    TopGainerLoserEntity(
                        ticker = loser.ticker,
                        price = loser.price,
                        changeAmount = loser.changeAmount,
                        changePercentage = loser.changePercentage,
                        type = "loser"
                    )
                }
                var topGainers = it.topGainers.map { gainer ->
                    TopGainerLoserEntity(
                        ticker = gainer.ticker,
                        price = gainer.price,
                        changeAmount = gainer.changeAmount,
                        changePercentage = gainer.changePercentage,
                        type = "gainer"
                    )
                }
                dao.deleteAll()
                dao.insertAll(topGainers + topLosers)
                val updated = dao.getAll()
                emit(Resource.Success(updated.toDomain()))
            }
            emit(Resource.Loading(false))
        }
    }

}