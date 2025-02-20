package com.dindaka.workshops_android.data.repository

import com.dindaka.workshops_android.data.local.db.dao.StorageDao
import com.dindaka.workshops_android.data.local.db.entities.StorageEntity
import com.dindaka.workshops_android.data.remote.ApiService
import com.dindaka.workshops_android.data.remote.dto.common.CommonResponse
import com.dindaka.workshops_android.data.remote.dto.mechanic.MechanicResponse
import com.dindaka.workshops_android.data.remote.dto.parts.StorageResponse
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.ServicesResponse
import com.dindaka.workshops_android.data.remote.dto.workshop.WorkshopsDataResponse
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkshopRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storageDao: StorageDao
) :
    WorkshopRepository {
    override suspend fun getDataUser(userId: String): Flow<WorkshopsDataResponse> = flow {
        emit(apiService.getDataTaller(userId))
    }

    override suspend fun getStorage(): Flow<StorageResponse> = flow {
        emit(apiService.getParts())
    }

    override suspend fun getMechanic(userId: String): Flow<MechanicResponse> = flow {
        emit(apiService.getMechanic(userId))
    }

    override suspend fun getSolicitudes(): Flow<ServicesResponse> = flow {
        emit(apiService.getSolicitudes())
    }

    override suspend fun getDetailPartOnWeb(userId: String): Flow<StorageResponse> = flow {
        emit(apiService.getPartOnWorkshopDetail(userId))
    }

    override suspend fun clearTable() {
        storageDao.clearStorageTable()
    }

    override suspend fun savedItemList(list: List<StorageEntity>) {
        storageDao.insertItems(list)
    }

    override suspend fun getItemsStorage(): List<StorageEntity> {
        return storageDao.getItems()
    }

    override suspend fun addSolicitud(serviceDTO: ServiceDTO): Flow<CommonResponse> = flow {
        emit(apiService.addSolicitud(serviceDTO))
    }

    override suspend fun updateSolicitud(serviceDTO: ServiceDTO): Flow<CommonResponse> = flow {
        emit(apiService.updateSolicitud(serviceDTO.uid ?: "", serviceDTO))
    }
}