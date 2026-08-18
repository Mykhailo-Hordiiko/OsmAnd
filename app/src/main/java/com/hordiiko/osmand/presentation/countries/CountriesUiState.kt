package com.hordiiko.osmand.presentation.countries

import com.hordiiko.osmand.presentation.common.model.RegionUi

sealed interface CountriesUiState {
    data object Loading : CountriesUiState
    data class Success(val regions: List<RegionUi>) : CountriesUiState
    data class Error(val message: String?) : CountriesUiState
}