package com.hordiiko.osmand.domain.model

data class RegionsTree(
    val countries: List<Region>,
    private val regions: Map<String, Region>
) {
    fun findById(id: String): Region? = regions[id]
}