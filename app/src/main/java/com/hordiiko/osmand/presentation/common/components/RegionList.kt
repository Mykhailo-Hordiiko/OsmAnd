package com.hordiiko.osmand.presentation.common.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hordiiko.osmand.presentation.common.model.RegionUi

@Composable
fun RegionList(
    regions: List<RegionUi>,
    onRegionClick: (RegionUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = regions,
            key = { it.id }
        ) { region ->
            RegionItem(
                regionUi = region,
                onClick = onRegionClick
            )
        }
    }
}