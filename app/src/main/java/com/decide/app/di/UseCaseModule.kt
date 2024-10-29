package com.decide.app.di

import com.decide.app.account.data.KladrClientImpl
import com.decide.app.account.domain.impl.CreateUserUseCaseImpl
import com.decide.app.account.domain.impl.GetAvatarUseCaseImpl
import com.decide.app.account.domain.impl.IsUserAuthUseCaseImpl
import com.decide.app.account.domain.impl.LogOutUseCaseImpl
import com.decide.app.account.domain.impl.SaveAvatarUseCaseImpl
import com.decide.app.account.domain.impl.SingInWithEmailUseCaseImpl
import com.decide.app.account.domain.impl.SingInWithVKUseCaseImpl
import com.decide.app.account.domain.impl.UpdateUserDataUseCaseImpl
import com.decide.app.account.domain.kladr.KladrClient
import com.decide.app.account.domain.useCase.CreateUserUseCase
import com.decide.app.account.domain.useCase.GetAvatarUseCase
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.account.domain.useCase.LogOutUseCase
import com.decide.app.account.domain.useCase.SaveAvatarUseCase
import com.decide.app.account.domain.useCase.SingInWithEmailUseCase
import com.decide.app.account.domain.useCase.SingInWithVKUseCase
import com.decide.app.account.domain.useCase.UpdateUserDataUseCase
import com.decide.app.activity.domain.ChangeVariableFirstLaunchUseCase
import com.decide.app.activity.domain.CheckForSync
import com.decide.app.activity.domain.InitApp
import com.decide.app.activity.domain.InitRemoteConfig
import com.decide.app.activity.domain.IsFirstLaunchUseCase
import com.decide.app.activity.domain.impl.ChangeVariableFirstLaunchUseCaseImpl
import com.decide.app.activity.domain.impl.CheckForSyncImpl
import com.decide.app.activity.domain.impl.InitAppImpl
import com.decide.app.activity.domain.impl.InitRemoteConfigImpl
import com.decide.app.activity.domain.impl.IsFirstLaunchUseCaseImpl
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl
import com.decide.app.feature.assay.assayProcess.domain.impl.GetAssayUseCaseImpl
import com.decide.app.feature.assay.assayProcess.domain.impl.SaveResultUseCaseImpl
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import com.decide.app.feature.profile.profileMain.domain.IsAuthUserUseCase
import com.decide.app.feature.profile.profileMain.domain.IsAuthUserUseCaseImpl
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

    @Binds
    abstract fun bindCreateUserUseCase(createUserUseCaseImpl: CreateUserUseCaseImpl): CreateUserUseCase

    @Binds
    abstract fun bindSaveAvatarUseCase(saveAvatarUseCaseImpl: SaveAvatarUseCaseImpl): SaveAvatarUseCase

    @Binds
    abstract fun bindUpdateUserDataUseCase(updateUserDataUseCaseImpl: UpdateUserDataUseCaseImpl): UpdateUserDataUseCase

    @Binds
    abstract fun bindGetAvatarUseCase(getAvatarUseCaseImpl: GetAvatarUseCaseImpl): GetAvatarUseCase

    @Binds
    abstract fun bindIsAuthUserUseCase(isAuthUserUseCaseImpl: IsAuthUserUseCaseImpl): IsAuthUserUseCase

    @Binds
    abstract fun bindIsUserAuthUseCase(isUserAuthUseCaseImpl: IsUserAuthUseCaseImpl): IsUserAuthUseCase

    @Binds
    abstract fun bindSingInUseCase(singInUseCaseImpl: SingInWithEmailUseCaseImpl): SingInWithEmailUseCase

    @Binds
    abstract fun bindLogOutUseCase(logOutUseCaseImpl: LogOutUseCaseImpl): LogOutUseCase

    @Binds
    abstract fun bindCheckForSync(checkForSyncImpl: CheckForSyncImpl): CheckForSync

    @Binds
    abstract fun bindKladrClient(kladrClientImpl: KladrClientImpl): KladrClient

    @Binds
    abstract fun bindInitRemoteConfig(initRemoteConfigImpl: InitRemoteConfigImpl): InitRemoteConfig

    @Binds
    abstract fun bindSingInWithVKUseCase(singInWithVKUseCaseImpl: SingInWithVKUseCaseImpl): SingInWithVKUseCase

    @Binds
    abstract fun bindIsFirstLaunchUseCase(isFirstLaunchUseCaseImpl: IsFirstLaunchUseCaseImpl): IsFirstLaunchUseCase

    @Binds
    abstract fun bindChangeVariableFirstLaunchUseCase(changeVariableFirstLaunchUseCaseImpl: ChangeVariableFirstLaunchUseCaseImpl): ChangeVariableFirstLaunchUseCase
}