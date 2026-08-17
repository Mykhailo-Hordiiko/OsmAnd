package com.hordiiko.osmand.domain.repository

import com.hordiiko.osmand.domain.model.Region

interface RegionsRepository {

    suspend fun getCountries(): Result<List<Region>>

    fun findRegion(id: String): Region?
}