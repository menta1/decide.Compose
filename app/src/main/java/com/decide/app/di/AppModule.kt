package com.decide.app.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.dao.TempDao
import com.decide.app.utils.NetworkChecker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebase() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseStorage() = Firebase.storage

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideNetworkUtils(connectivityManager: ConnectivityManager): NetworkChecker {
        return NetworkChecker(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database.db").build()
    }

    @Provides
    @Singleton
    fun provideAssayDao(database: AppDatabase): AssayDao {
        return database.assayDao()
    }

    @Provides
    @Singleton
    fun provideTempDao(database: AppDatabase): TempDao {
        return database.tempDao()
    }

    @Provides
    @Singleton
    fun coroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

//    @Provides
//    fun provideRemoteAssayStorage() = RemoteAssayStorageImpl
}