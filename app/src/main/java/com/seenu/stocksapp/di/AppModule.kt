package com.seenu.stocksapp.di

import android.app.Application
import androidx.room.Room
import com.seenu.stocksapp.data.local.StocksDatabase
import com.seenu.stocksapp.data.local.dao.CompanyOverviewDao
import com.seenu.stocksapp.data.local.dao.WatchlistDao
import com.seenu.stocksapp.data.remote.StocksApi
import com.seenu.stocksapp.util.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi(): StocksApi{
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
    @Provides
    @Singleton
    fun providesStockDatabase(app: Application): StocksDatabase{
        return Room.databaseBuilder(
            app,
            StocksDatabase::class.java,
            "stocks"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideWatchlistDao(db: StocksDatabase): WatchlistDao = db.watchListDao

    @Provides
    @Singleton
    fun provideCompanyOverviewDao(db: StocksDatabase): CompanyOverviewDao {
        return db.companyOverviewDao
    }
}