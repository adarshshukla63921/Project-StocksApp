package com.seenu.stocksapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seenu.stocksapp.data.local.dao.CompanyListingDao
import com.seenu.stocksapp.data.local.dao.TopGainersLosersDao
import com.seenu.stocksapp.data.local.dao.WatchlistDao
import com.seenu.stocksapp.data.local.entities.CompanyListingEntity
import com.seenu.stocksapp.data.local.entities.CompanyOverviewEntity
import com.seenu.stocksapp.data.local.entities.TopGainerLoserEntity
import com.seenu.stocksapp.data.local.entities.WatchlistCompanyEntity
import com.seenu.stocksapp.data.local.entities.WatchlistEntity
import com.seenu.stocksapp.data.local.entities.WatchlistStock

@Database(
    entities = [
        TopGainerLoserEntity::class,
        CompanyOverviewEntity::class,
        WatchlistEntity::class,
        WatchlistCompanyEntity::class,
        WatchlistStock::class,
        CompanyListingEntity::class],
    version = 3
)
abstract class StocksDatabase: RoomDatabase(){
    abstract val topGainersLosersDao: TopGainersLosersDao
    abstract val watchListDao : WatchlistDao
    abstract val companyListingDao : CompanyListingDao
    abstract val companyOverviewDao : com.seenu.stocksapp.data.local.dao.CompanyOverviewDao
}