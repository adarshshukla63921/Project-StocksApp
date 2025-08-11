package com.seenu.stocksapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seenu.stocksapp.data.local.entities.TopGainerLoserEntity

@Dao
interface TopGainersLosersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TopGainerLoserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TopGainerLoserEntity>)

    @Query("DELETE FROM top_gainers_losers WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM top_gainers_losers WHERE ticker = :ticker")
    suspend fun deleteByTicker(ticker: String)

    @Query("DELETE FROM top_gainers_losers")
    suspend fun deleteAll()

    @Query("SELECT * FROM top_gainers_losers WHERE type = :type")
    suspend fun getByType(type: String): List<TopGainerLoserEntity>

    @Query("SELECT * FROM top_gainers_losers")
    suspend fun getAll(): List<TopGainerLoserEntity>
}