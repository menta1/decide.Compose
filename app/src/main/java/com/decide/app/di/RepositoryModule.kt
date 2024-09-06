package com.decide.app.di

import com.decide.app.account.authenticationClient.AuthenticationClient
import com.decide.app.account.authenticationClient.AuthenticationClientImpl
import com.decide.app.account.data.AccountRepositoryImpl
import com.decide.app.account.domain.AccountRepository
import com.decide.app.activity.repository.MainRepository
import com.decide.app.activity.repository.MainRepositoryImpl
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.database.remote.RemoteAssayStorageImpl
import com.decide.app.feature.assay.assayDescription.ui.data.AssayDescriptionRepository
import com.decide.app.feature.assay.assayDescription.ui.data.AssayDescriptionRepositoryImpl
import com.decide.app.feature.assay.assayMain.data.AssayMainRepository
import com.decide.app.feature.assay.assayMain.data.AssayMainRepositoryImpl
import com.decide.app.feature.assay.assayProcess.data.AssayProcessRepositoryImpl
import com.decide.app.feature.assay.assayProcess.domain.useCase.AssayProcessRepository
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepository
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepositoryImpl
import com.decide.app.feature.category.mainCategory.data.CategoryRepository
import com.decide.app.feature.category.mainCategory.data.CategoryRepositoryImpl
import com.decide.app.feature.category.specificCategory.data.CategoriesSpecificRepository
import com.decide.app.feature.category.specificCategory.data.CategoriesSpecificRepositoryImpl
import com.decide.app.feature.passed.data.PassedScreenRepository
import com.decide.app.feature.passed.data.PassedScreenRepositoryImpl
import com.decide.app.feature.profile.profileMain.data.ProfileRepositoryImpl
import com.decide.app.feature.profile.profileMain.domain.ProfileRepository
import com.decide.app.feature.profile.settings.data.SettingsRepository
import com.decide.app.feature.profile.settings.data.SettingsRepositoryImpl
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

    @Binds
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindCategoriesSpecificRepository(categoriesSpecificRepositoryImpl: CategoriesSpecificRepositoryImpl): CategoriesSpecificRepository

    @Binds
    abstract fun bindPassedScreenRepository(passedScreenRepositoryImpl: PassedScreenRepositoryImpl): PassedScreenRepository

    @Binds
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun bindNetworkClient(authenticationClientImpl: AuthenticationClientImpl): AuthenticationClient

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}