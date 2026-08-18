package com.hordiiko.osmand.presentation.common.model

sealed interface DownloadStatus {
    data object NotStarted : DownloadStatus
    data object Queued : DownloadStatus
    data class Processing(val progress: Int) : DownloadStatus
    data object Done : DownloadStatus
}