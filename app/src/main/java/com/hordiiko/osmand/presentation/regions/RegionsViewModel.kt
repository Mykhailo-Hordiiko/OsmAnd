package com.hordiiko.osmand.presentation.regions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hordiiko.osmand.domain.repository.RegionsRepository
import com.hordiiko.osmand.navigation.ARG_REGION_ID
import com.hordiiko.osmand.presentation.common.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegionsViewModel @Inject constructor(
    private val repository: RegionsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val regionId: String = checkNotNull(savedStateHandle[ARG_REGION_ID])
    private val region = repository.findRegion(regionId)

    val uiState: RegionsUiState = RegionsUiState(
        title = region?.name.orEmpty(),
        regions = region?.subRegions?.toUi().orEmpty()
    )
}