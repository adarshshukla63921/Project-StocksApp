package com.seenu.stocksapp.data.repository

import com.seenu.stocksapp.data.local.dao.WatchlistDao
import com.seenu.stocksapp.data.local.entities.WatchlistCompanyEntity
import com.seenu.stocksapp.data.local.entities.WatchlistEntity
import com.seenu.stocksapp.data.local.entities.WatchlistStock
import com.seenu.stocksapp.domain.repository.WatchlistRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistRepoImpl @Inject constructor(
    private val dao: WatchlistDao
) : WatchlistRepo {


}