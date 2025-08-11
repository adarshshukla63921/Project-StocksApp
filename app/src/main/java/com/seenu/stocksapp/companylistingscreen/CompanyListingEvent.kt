package com.seenu.stocksapp.companylistingscreen

sealed class CompanyListingEvent {
    object Refresh : CompanyListingEvent()
    data class OnSearchQueryChange(val query : String) : CompanyListingEvent()
}