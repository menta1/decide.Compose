package com.decide.app.di

import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.database.remote.assay.RemoteAssayStorageImpl
import com.decide.app.feature.assay.assayDescription.ui.data.AssayDescriptionRepository
import com.decide.app.feature.assay.assayDescription.ui.data.AssayDescriptionRepositoryImpl
import com.decide.app.feature.assay.assayProcess.data.AssayProcessRepository
import com.decide.app.feature.assay.assayProcess.data.AssayProcessRepositoryImpl
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepository
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepositoryImpl
import com.decide.app.feature.assay.mainAssay.data.AssayMainRepository
import com.decide.app.feature.assay.mainAssay.data.AssayMainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAssayMainRepository(assayMainRepositoryImpl: AssayMainRepositoryImpl): AssayMainRepository

    @Binds
    abstract fun bindAssayDescriptionRepository(assayDescriptionRepositoryImpl: AssayDescriptionRepositoryImpl): AssayDescriptionRepository

    @Binds
    abstract fun bindAssayProcessRepository(assayProcessRepositoryImpl: AssayProcessRepositoryImpl): AssayProcessRepository

    @Binds
    abstract fun bindRemoteAssayStorage(remoteAssayStorageImpl: RemoteAssayStorageImpl): RemoteAssayStorage

    @Binds
    abstract fun bindAssayWithResultRepository(assayWithResultRepositoryImpl: AssayWithResultRepositoryImpl): AssayWithResultRepository
}