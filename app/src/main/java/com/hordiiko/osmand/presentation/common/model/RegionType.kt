package com.hordiiko.osmand.presentation.common.model

sealed interface RegionType {
    data object Node : RegionType

    data class Downloadable(
        val fileName: String,
        val downloadStatus: DownloadStatus
    ) : RegionType

    data object None : RegionType
}