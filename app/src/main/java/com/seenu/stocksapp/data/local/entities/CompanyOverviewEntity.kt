package com.seenu.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_overview")
data class CompanyOverviewEntity(
    @PrimaryKey()val symbol: String,
    val name: String,
    val description: String,
    val marketCap: String,
    val peRatio: String,
    val profitMargin: String,
    val sector: String,
    val industry: String,
    val weekHigh52: String,
    val weekLow52: String
)
