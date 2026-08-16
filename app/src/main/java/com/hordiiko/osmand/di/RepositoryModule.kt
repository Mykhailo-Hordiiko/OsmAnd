package com.hordiiko.osmand.di

import com.hordiiko.osmand.data.repository.RegionsRepositoryImpl
import com.hordiiko.osmand.domain.repository.RegionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRegionsRepository(impl: RegionsRepositoryImpl): RegionsRepository
}