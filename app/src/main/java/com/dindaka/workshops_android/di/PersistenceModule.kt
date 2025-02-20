package com.dindaka.workshops_android.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.dindaka.workshops_android.data.local.db.AppDatabase
import com.dindaka.workshops_android.data.local.db.DATABASE_NAME
import com.dindaka.workshops_android.data.local.db.dao.StorageDao
import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.local.sharedpreferences.NAME_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideMyPreferences(preferences: SharedPreferences) = MyPreferences(preferences)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {

        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStorageDao(appDatabase: AppDatabase): StorageDao {
        return appDatabase.storageDao()
    }
}