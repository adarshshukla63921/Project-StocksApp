package com.seenu.stocksapp.watchlistscreen

import androidx.lifecycle.ViewModel
import com.seenu.stocksapp.domain.repository.WatchlistRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repo : WatchlistRepo
) : ViewModel(){

}