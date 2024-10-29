package com.decide.app.di

import com.decide.app.account.adsManager.AdsManager
import com.decide.app.account.adsManager.AdsManagerImpl
import com.decide.app.account.authenticationClient.FirebaseAuthenticationClient
import com.decide.app.account.authenticationClient.FirebaseAuthenticationClientImpl
import com.decide.app.account.data.AccountRepositoryImpl
import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.statisticsClient.StatisticsClient
import com.decide.app.account.statisticsClient.StatisticsClientImpl
import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.data.MainRepositoryImpl
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.database.remote.RemoteAssayStorageImpl
import com.decide.app.feature.assay.assayDescription.data.AssayDescriptionRepository
import com.decide.app.feature.assay.assayDescription.data.AssayDescriptionRepositoryImpl
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
import com.decide.app.feature.profile.editProfile.data.EditProfileRepository
import com.decide.app.feature.profile.editProfile.data.EditProfileRepositoryImpl
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
    abstract fun bindNetworkClient(authenticationClientImpl: FirebaseAuthenticationClientImpl): FirebaseAuthenticationClient

    @Binds
    abstract fun bindStatisticsClient(statisticsClientImpl: StatisticsClientImpl): StatisticsClient

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    abstract fun bindEditProfileRepository(editProfileRepositoryImpl: EditProfileRepositoryImpl): EditProfileRepository

    @Binds
    abstract fun bindAdsManager(adsManagerImpl: AdsManagerImpl): AdsManager
}