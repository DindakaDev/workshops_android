package com.dindaka.workshops_android.domain.usecase.service

import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.StatusService
import com.dindaka.workshops_android.data.remote.dto.service.TypeService
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetServicesUseCase(
    private val repository: WorkshopRepository,
) {
    suspend operator fun invoke(filterByType: TypeService?, filterByStatus: StatusService?): Flow<List<ServiceDTO>> =
        repository.getSolicitudes().map {
            val list = mutableListOf<ServiceDTO>()
            it.filter { (filterByType == null || it.value.tipo == filterByType.name) && ( filterByStatus == null || it.value.estatus == filterByStatus.name) }.map {
                val serviceDTO = it.value
                serviceDTO.uid = it.key
                list.add(serviceDTO)
            }
            list.toList()
        }.catch {
            emptyList<ServiceDTO>()
        }
}