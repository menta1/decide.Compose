package com.decide.app.di

import com.decide.app.activity.domain.InitApp
import com.decide.app.activity.domain.InitAppImpl
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl
import com.decide.app.feature.assay.assayProcess.domain.impl.GetAssayUseCaseImpl
import com.decide.app.feature.assay.assayProcess.domain.impl.SaveResultUseCaseImpl
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetAssayUseCase(assayGetAssayUseCaseImpl: GetAssayUseCaseImpl): GetAssayUseCase

    @Binds
    abstract fun bindSaveResultUseCase(assaySaveResultUseCaseImpl: SaveResultUseCaseImpl): SaveResultUseCase

    @Binds
    abstract fun bindAnalysisKeyAssay(assayAnalysisKeyAssayImpl: AnalysisKeyAssayImpl): AnalysisKeyAssay

    @Binds
    abstract fun bindInitApp(initAppImpl: InitAppImpl): InitApp

}