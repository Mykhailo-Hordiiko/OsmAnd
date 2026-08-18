package com.hordiiko.osmand.presentation.regions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hordiiko.osmand.presentation.common.components.AppTopAppBar
import com.hordiiko.osmand.presentation.common.components.RegionList
import com.hordiiko.osmand.presentation.common.model.RegionType

@Composable
fun RegionsScreen(
    onNodeClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegionsViewModel = hiltViewModel()
) {
    val uiState: RegionsUiState = viewModel.uiState

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = uiState.title,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        RegionList(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            regions = uiState.regions,
            onRegionClick = { region ->
                when (region.type) {
                    is RegionType.Node -> onNodeClick(region.id)
                    is RegionType.Downloadable -> {}
                    is RegionType.None -> Unit
                }
            }
        )
    }
}