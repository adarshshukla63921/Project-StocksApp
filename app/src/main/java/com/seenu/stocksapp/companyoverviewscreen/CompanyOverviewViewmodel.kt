package com.seenu.stocksapp.companyoverviewscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seenu.stocksapp.domain.model.CompanyOverview
import com.seenu.stocksapp.domain.model.IntradayInfo
import com.seenu.stocksapp.domain.repository.CompanyOverviewRepo
import com.seenu.stocksapp.domain.repository.IntradayInfoRepo
import com.seenu.stocksapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyOverviewViewmodel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo : CompanyOverviewRepo,
    private val intradayRepo : IntradayInfoRepo
): ViewModel() {
    var state by mutableStateOf(CompanyOverviewState())
        private set

    init {
        loadData()
    }

    fun loadData() {
        val ticker = savedStateHandle.get<String>("ticker") ?: return
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val overviewDeferred = async { repo.getCompanyOverview(ticker) }
            val intradayDeferred = async { intradayRepo.getIntradayInfo(ticker) }

            val overviewResult = overviewDeferred.await()
            val intradayResult = intradayDeferred.await()

            handleOverviewResult(overviewResult)
            handleIntradayResult(intradayResult)

            state = state.copy(isLoading = false)
        }
    }

    fun onRefresh() {
        Log.d("TAG","onRefresh")
        val ticker = savedStateHandle.get<String>("ticker") ?: return
        viewModelScope.launch {
            state = state.copy(isRefreshing = true)
            val overviewDeferred = async { repo.getCompanyOverview(ticker, fetchFromRemote = true) }
            val intradayDeferred = async { intradayRepo.getIntradayInfo(ticker) }

            val overviewResult = overviewDeferred.await()
            val intradayResult = intradayDeferred.await()

            handleOverviewResult(overviewResult)
            handleIntradayResult(intradayResult)

            state = state.copy(isRefreshing = false)
        }
    }

    private fun handleOverviewResult(result: Resource<CompanyOverview>) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    company = result.data,
                    error = null
                )
            }
            is Resource.Error -> {
                state = state.copy(
                    company = result.data,
                    error = result.message
                )
            }
            else -> Unit
        }
    }
    private fun handleIntradayResult(result: Resource<List<IntradayInfo>>) {
        when (result) {
            is Resource.Success -> {
                state = state.copy(
                    stockInfo = result.data?:emptyList(),
                    error = null
                )
            }
            is Resource.Error -> {
                state = state.copy(
                    error = result.message
                )
            }
            else -> Unit
        }
    }
}