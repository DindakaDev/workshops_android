package com.dindaka.workshops_android.di

import com.dindaka.workshops_android.data.local.db.dao.StorageDao
import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.ApiService
import com.dindaka.workshops_android.data.repository.LoginRepositoryImpl
import com.dindaka.workshops_android.data.repository.WorkshopRepositoryImpl
import com.dindaka.workshops_android.domain.repository.LoginRepository
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService, myPreferences: MyPreferences): LoginRepository =
        LoginRepositoryImpl(apiService, myPreferences)

    @Provides
    @Singleton
    fun provideWorkshopRepository(apiService: ApiService, storageDao: StorageDao): WorkshopRepository =
        WorkshopRepositoryImpl(apiService, storageDao)
}