package com.seenu.stocksapp.topgainerslosersscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seenu.stocksapp.domain.repository.TopGainerLoserRepo
import com.seenu.stocksapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TopGainersLosersViewmodel @Inject constructor(
    private val repo : TopGainerLoserRepo
) : ViewModel() {
    private val _state = MutableStateFlow(TopGainersLosersState())
    val state : StateFlow<TopGainersLosersState> = _state

    init {
        getTopGainersLosers()
    }

    fun getTopGainersLosers(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            if(fetchFromRemote){
                _state.value=_state.value.copy(isRefreshing = true)
            }
            repo.getTopGainersLosers(fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = result.isLoading, error = null)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            topGainers = result.data?.topGainers ?: emptyList(),
                            topLosers = result.data?.topLosers ?: emptyList(),
                            isLoading = false,
                            isRefreshing = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
    fun onRefresh(){
        getTopGainersLosers(fetchFromRemote = true)
    }
}