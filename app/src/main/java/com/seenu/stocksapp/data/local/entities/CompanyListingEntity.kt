package com.seenu.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_listing")
data class CompanyListingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val symbol: String,
    val name : String,
    val exchange : String
)
