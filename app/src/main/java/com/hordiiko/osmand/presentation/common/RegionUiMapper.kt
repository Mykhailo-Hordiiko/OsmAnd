package com.hordiiko.osmand.presentation.common

import com.hordiiko.osmand.domain.model.Region

fun Region.toUi(): RegionUi =
    RegionUi(
        id = id,
        name = name,
        type = when {
            subRegions.isNotEmpty() ->
                RegionType.Node

            fileName != null ->
                RegionType.Downloadable(
                    fileName = fileName,
                    downloadStatus = DownloadStatus.NotStarted
                )

            else ->
                RegionType.None
        }
    )

fun List<Region>.toUi(): List<RegionUi> =
    map { it.toUi() }