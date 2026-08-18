package com.hordiiko.osmand.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hordiiko.osmand.R
import com.hordiiko.osmand.presentation.common.model.RegionType
import com.hordiiko.osmand.presentation.common.model.RegionUi
import com.hordiiko.osmand.presentation.theme.iconTintDefault
import com.hordiiko.osmand.presentation.theme.size
import com.hordiiko.osmand.presentation.theme.spacing

private val ItemHeight = 52.dp
private val ItemDividerThickness = 1.dp
private val ItemTextSize = 16.sp

private const val MAX_WEIGHT = 1F

@Composable
fun RegionItem(
    regionUi: RegionUi,
    onClick: (RegionUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ItemHeight)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick(regionUi) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeadingIcon(regionUi.type)

        Column {
            Row(
                modifier = Modifier
                    // compensates divider height for vertical centering
                    .padding(
                        top = ItemDividerThickness
                    )
                    .weight(MAX_WEIGHT),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemText(regionUi.name)
                TrailingIcon(regionUi.type)
            }

            ItemDivider()
        }
    }
}

@Composable
private fun LeadingIcon(type: RegionType) {
    val iconRes: Int =
        if (type is RegionType.Node) R.drawable.ic_world_globe_dark else R.drawable.ic_map

    Icon(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium
            )
            .size(MaterialTheme.size.small),
        painter = painterResource(iconRes),
        contentDescription = null,
        tint = iconTintDefault
    )
}

@Composable
private fun RowScope.ItemText(name: String) {
    Text(
        modifier = Modifier.weight(MAX_WEIGHT),
        text = name,
        fontSize = ItemTextSize,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TrailingIcon(type: RegionType) {
    if (type !is RegionType.None) {
        Icon(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.medium
                )
                .size(MaterialTheme.size.small),
            painter = painterResource(R.drawable.ic_action_import),
            contentDescription = null,
            tint = iconTintDefault
        )
    }
}

@Composable
private fun ItemDivider() {
    HorizontalDivider(
        thickness = ItemDividerThickness,
        color = MaterialTheme.colorScheme.outline
    )
}