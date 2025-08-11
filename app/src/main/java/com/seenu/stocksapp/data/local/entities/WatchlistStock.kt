package com.seenu.stocksapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "watchlist_stocks",
    foreignKeys = [
        ForeignKey(
            entity = WatchlistEntity::class,
            parentColumns = ["id"],
            childColumns = ["watchlistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("watchlistId")]
)
data class WatchlistStock(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val watchlistId: Long,
    val symbol: String,
    val name: String,
    val exchange: String
)
