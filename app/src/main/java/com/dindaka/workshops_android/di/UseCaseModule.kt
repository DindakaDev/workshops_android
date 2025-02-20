package com.dindaka.workshops_android.di

import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.domain.repository.LoginRepository
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import com.dindaka.workshops_android.domain.usecase.login.LoginUseCase
import com.dindaka.workshops_android.domain.usecase.mechanic.GetMechanicsServerUseCase
import com.dindaka.workshops_android.domain.usecase.parts.CheckPartExistOnStorageUseCase
import com.dindaka.workshops_android.domain.usecase.parts.GetListPartsUseCase
import com.dindaka.workshops_android.domain.usecase.parts.GetStorageFromServerUseCase
import com.dindaka.workshops_android.domain.usecase.service.GetServicesUseCase
import com.dindaka.workshops_android.domain.usecase.service.UpdateSolicitudUseCase
import com.dindaka.workshops_android.domain.usecase.service.UploadSolicitudUseCase
import com.dindaka.workshops_android.domain.usecase.workshop.GetDataWorkshopUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideDoLogin(repository: LoginRepository) = LoginUseCase(repository)

    @Provides
    fun provideGetDataUseCase(repository: WorkshopRepository, myPreferences: MyPreferences) =
        GetDataWorkshopUseCase(repository, myPreferences)

    @Provides
    fun provideGetListPartsUseCase(repository: WorkshopRepository) =
        GetListPartsUseCase(repository)

    @Provides
    fun provideGetStorageFromServerUseCase(repository: WorkshopRepository) =
        GetStorageFromServerUseCase(repository)

    @Provides
    fun provideCheckPartExistOnStorageUseCase(
        repository: WorkshopRepository,
        myPreferences: MyPreferences
    ) =
        CheckPartExistOnStorageUseCase(repository, myPreferences)

    @Provides
    fun provideGetMechanicsServerUseCase(
        repository: WorkshopRepository,
        myPreferences: MyPreferences
    ) =
        GetMechanicsServerUseCase(repository, myPreferences)

    @Provides
    fun provideUploadSolicitudUseCase(
        repository: WorkshopRepository
    ) =
        UploadSolicitudUseCase(repository)

    @Provides
    fun provideGetServicesUseCase(
        repository: WorkshopRepository
    ) =
        GetServicesUseCase(repository)

    @Provides
    fun provideUpdateSolicitudUseCase(repository: WorkshopRepository) =
        UpdateSolicitudUseCase(repository)

}