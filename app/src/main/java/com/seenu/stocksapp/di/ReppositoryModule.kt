package com.seenu.stocksapp.di

import com.seenu.stocksapp.data.csv.CompanyListingParser
import com.seenu.stocksapp.data.csv.IntradayInfoParser
import com.seenu.stocksapp.data.csv.csvParser
import com.seenu.stocksapp.data.repository.CompanyListingRepoImpl
import com.seenu.stocksapp.data.repository.CompanyOverviewRepoImpl
import com.seenu.stocksapp.data.repository.IntradayInfoRepoImpl
import com.seenu.stocksapp.data.repository.TopGainerLoserRepoImpl
import com.seenu.stocksapp.data.repository.WatchlistRepoImpl
import com.seenu.stocksapp.domain.model.CompanyListing
import com.seenu.stocksapp.domain.model.IntradayInfo
import com.seenu.stocksapp.domain.repository.CompanyListingRepo
import com.seenu.stocksapp.domain.repository.CompanyOverviewRepo
import com.seenu.stocksapp.domain.repository.IntradayInfoRepo
import com.seenu.stocksapp.domain.repository.TopGainerLoserRepo
import com.seenu.stocksapp.domain.repository.WatchlistRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
   @Binds
   @Singleton
   abstract fun bindCompanyListingParser(companyListingParser: CompanyListingParser) : csvParser<CompanyListing>

   @Binds
   @Singleton
   abstract fun bindCompanyListingRepo(companyListingRepoImpl: CompanyListingRepoImpl) : CompanyListingRepo

   @Binds
   @Singleton
   abstract fun bindTopGainerLoserRepo(impl: TopGainerLoserRepoImpl): TopGainerLoserRepo

   @Binds
   @Singleton
   abstract fun bindCompanyOverviewRepo(impl: CompanyOverviewRepoImpl): CompanyOverviewRepo

   @Binds
   @Singleton
   abstract fun bindIntradayInfoRepo(impl: IntradayInfoRepoImpl): IntradayInfoRepo

   @Binds
   @Singleton
   abstract fun bindIntradayInfoParser(impl: IntradayInfoParser): csvParser<IntradayInfo>

   @Binds
   @Singleton
   abstract fun bindWatchlistRepo(impl: WatchlistRepoImpl): WatchlistRepo

}