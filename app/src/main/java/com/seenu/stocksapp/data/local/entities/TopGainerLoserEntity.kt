package com.seenu.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "top_gainers_losers")
data class TopGainerLoserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val ticker: String,
    val price: String,
    val changeAmount: String,
    val changePercentage: String,
    val type: String
)
