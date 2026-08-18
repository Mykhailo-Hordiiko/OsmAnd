package com.hordiiko.osmand.presentation.regions

import com.hordiiko.osmand.presentation.common.model.RegionUi

data class RegionsUiState(
    val title: String,
    val regions: List<RegionUi>
)