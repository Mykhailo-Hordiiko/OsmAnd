package com.hordiiko.osmand.presentation.countries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hordiiko.osmand.R
import com.hordiiko.osmand.presentation.common.components.AppTopAppBar
import com.hordiiko.osmand.presentation.common.components.RegionList
import com.hordiiko.osmand.presentation.common.model.RegionType
import com.hordiiko.osmand.presentation.common.model.RegionUi
import com.hordiiko.osmand.presentation.theme.spacing

@Composable
fun CountriesScreen(
    onNodeClick: (String) -> Unit,
    viewModel: CountriesViewModel = hiltViewModel()
) {
    val uiState: CountriesUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(R.string.download_maps)
            )
        }
    ) { padding ->
        CountriesContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            uiState = uiState,
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

@Composable
private fun CountriesContent(
    uiState: CountriesUiState,
    onRegionClick: (RegionUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is CountriesUiState.Loading ->
                LoadingContent()

            is CountriesUiState.Success ->
                RegionList(
                    regions = uiState.regions,
                    onRegionClick = onRegionClick
                )

            is CountriesUiState.Error ->
                ErrorContent(uiState.message)
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Text(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.spacing.small
                ),
            text = stringResource(R.string.loading)
        )
    }
}

@Composable
private fun ErrorContent(message: String?) {
    Text(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.small
            ),
        text = message ?: stringResource(R.string.error_unknown)
    )
}