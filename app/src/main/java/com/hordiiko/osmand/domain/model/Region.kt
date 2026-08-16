package com.hordiiko.osmand.domain.model

data class Region(
    val id: String,
    val name: String,
    val fileName: String?,
    val subRegions: List<Region> = emptyList()
)