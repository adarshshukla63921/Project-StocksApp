package com.seenu.stocksapp.topgainerslosersscreen

sealed class TopGainersLosersEvent {
    data class OnCardClick(val ticker: String) : TopGainersLosersEvent()
    data class OnViewAllClick(val type: ViewAllType) : TopGainersLosersEvent()
}