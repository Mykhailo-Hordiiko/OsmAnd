package com.hordiiko.osmand.data.repository

import android.content.Context
import com.hordiiko.osmand.data.local.parseRegionsTree
import com.hordiiko.osmand.domain.model.Region
import com.hordiiko.osmand.domain.model.RegionsTree
import com.hordiiko.osmand.domain.repository.RegionsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegionsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RegionsRepository {

    private var regionsTreeCache: RegionsTree? = null

    override suspend fun getCountries(): List<Region> {
        val tree: RegionsTree =
            regionsTreeCache ?: withContext(Dispatchers.IO) {
                parseRegionsTree(context)
            }.also {
                regionsTreeCache = it
            }

        return tree.countries
    }

    override fun findRegion(id: String): Region? = regionsTreeCache?.findById(id)
}