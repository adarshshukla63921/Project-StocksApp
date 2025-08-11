package com.seenu.stocksapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seenu.stocksapp.data.local.entities.CompanyOverviewEntity

@Dao
interface CompanyOverviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCompanyOverview(entity: CompanyOverviewEntity)

    @Query("SELECT * FROM company_overview WHERE symbol = :symbol LIMIT 1")
    suspend fun getCompanyOverview(symbol: String): CompanyOverviewEntity?

    @Query("DELETE FROM company_overview WHERE symbol = :symbol")
    suspend fun deleteCompanyOverview(symbol: String)
} 