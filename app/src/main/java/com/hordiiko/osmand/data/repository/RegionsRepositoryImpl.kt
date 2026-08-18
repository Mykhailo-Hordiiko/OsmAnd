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
import kotlin.coroutines.cancellation.CancellationException

class RegionsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RegionsRepository {

    private var regionsTreeCache: RegionsTree? = null

    override suspend fun getCountries(): Result<List<Region>> =
        try {
            val tree: RegionsTree =
                regionsTreeCache ?: withContext(Dispatchers.IO) {
                    parseRegionsTree(context)
                }.also {
                    regionsTreeCache = it
                }

            Result.success(tree.countries.sortedBy { it.name })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }

    override fun findRegion(id: String): Region? = regionsTreeCache?.findById(id)
}