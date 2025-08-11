package com.seenu.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "watchlist_company",
    primaryKeys = ["watchlistId", "symbol"],
    foreignKeys = [
        ForeignKey(
            entity = WatchlistEntity::class,
            parentColumns = ["id"],
            childColumns = ["watchlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CompanyOverviewEntity::class,
            parentColumns = ["symbol"],
            childColumns = ["symbol"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WatchlistCompanyEntity(
    val watchlistId: Int,
    val symbol: String,
    val addedAt: Long = System.currentTimeMillis()
)