package com.seenu.stocksapp.data.mappers

import com.seenu.stocksapp.data.local.entities.CompanyListingEntity
import com.seenu.stocksapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing{
    return CompanyListing(
        name=name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity{
    return CompanyListingEntity(
        name=name,
        symbol = symbol,
        exchange = exchange,
    )
}