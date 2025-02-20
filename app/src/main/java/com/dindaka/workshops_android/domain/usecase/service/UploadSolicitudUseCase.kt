package com.dindaka.workshops_android.domain.usecase.service

import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UploadSolicitudUseCase(
    private val repository: WorkshopRepository,
) {
    suspend operator fun invoke(serviceDTO: ServiceDTO): Flow<Boolean> =
        repository.addSolicitud(serviceDTO).map {
            true
        }.catch {
            false
        }
}