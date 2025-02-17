package com.dindaka.workshops_android.di

import com.dindaka.workshops_android.domain.repository.LoginRepository
import com.dindaka.workshops_android.domain.usecase.login.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideDoLogin(repository: LoginRepository) = LoginUseCase(repository)
}