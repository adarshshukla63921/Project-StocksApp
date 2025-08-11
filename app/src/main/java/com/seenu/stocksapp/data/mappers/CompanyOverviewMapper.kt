package com.seenu.stocksapp.data.mappers

import com.seenu.stocksapp.data.local.entities.CompanyOverviewEntity
import com.seenu.stocksapp.data.remote.dto.CompanyOverviewResponse
import com.seenu.stocksapp.domain.model.CompanyOverview


fun CompanyOverviewEntity.toDomain(): CompanyOverview {
    return CompanyOverview(
        ticker=this.symbol,
        name = this.name,
        description = this.description,
        marketCap = this.marketCap,
        peRatio = this.peRatio,
        profitMargin = this.profitMargin,
        sector = this.sector,
        industry = this.industry,
        weekHigh52 = this.weekHigh52,
        weekLow52 = this.weekLow52
    )
}

fun CompanyOverviewResponse.toEntity(): com.seenu.stocksapp.data.local.entities.CompanyOverviewEntity {
    return com.seenu.stocksapp.data.local.entities.CompanyOverviewEntity(
        symbol = this.ticker ?: "",
        name = this.name ?: "",
        description = this.description ?: "",
        marketCap = this.marketCap ?: "",
        peRatio = this.peRatio ?: "",
        profitMargin = this.profitMargin ?: "",
        sector = this.sector ?: "",
        industry = this.industry ?: "",
        weekHigh52 = this.weekHigh52 ?: "",
        weekLow52 = this.weekLow52 ?: ""
    )
}

