package com.seenu.stocksapp.data.mappers

import com.seenu.stocksapp.data.local.entities.TopGainerLoserEntity
import com.seenu.stocksapp.domain.model.TopGainerLoser
import com.seenu.stocksapp.domain.model.TopGainersLosers

fun TopGainerLoser.toEntity(type:String) : TopGainerLoserEntity{
    return TopGainerLoserEntity(
        ticker = this.ticker,
        price = this.price,
        changeAmount = this.changeAmount,
        changePercentage = this.changePercentage,
        type = type
    )
}
fun TopGainerLoserEntity.toDomain() : TopGainerLoser{
    return TopGainerLoser(
        ticker = this.ticker,
        price = this.price,
        changeAmount = this.changeAmount,
        changePercentage = this.changePercentage
    )
}
fun List<TopGainerLoserEntity>?.toDomain(): TopGainersLosers {
    val gainers = this?.filter { it.type == "gainer" }?.map { it.toDomain() } ?: emptyList()
    val losers = this?.filter { it.type == "loser" }?.map { it.toDomain() } ?: emptyList()
    return TopGainersLosers(topGainers = gainers, topLosers = losers)
}