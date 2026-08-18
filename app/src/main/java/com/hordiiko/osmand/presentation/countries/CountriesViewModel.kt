package com.hordiiko.osmand.presentation.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hordiiko.osmand.domain.repository.RegionsRepository
import com.hordiiko.osmand.presentation.common.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: RegionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val uiState: StateFlow<CountriesUiState> = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = CountriesUiState.Loading

            repository.getCountries()
                .onSuccess { countries ->
                    _uiState.value = CountriesUiState.Success(countries.toUi())
                }
                .onFailure { error ->
                    _uiState.value = CountriesUiState.Error(error.message)
                }
        }
    }
}