package com.dindaka.workshops_android.domain.repository

import com.dindaka.workshops_android.data.local.db.entities.StorageEntity
import com.dindaka.workshops_android.data.remote.dto.common.CommonResponse
import com.dindaka.workshops_android.data.remote.dto.mechanic.MechanicResponse
import com.dindaka.workshops_android.data.remote.dto.parts.StorageResponse
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.ServicesResponse
import com.dindaka.workshops_android.data.remote.dto.workshop.WorkshopsDataResponse
import kotlinx.coroutines.flow.Flow

interface WorkshopRepository {
    suspend fun getSolicitudes(): Flow<ServicesResponse>

    suspend fun getDataUser(userId: String): Flow<WorkshopsDataResponse>

    suspend fun getStorage(): Flow<StorageResponse>

    suspend fun getMechanic(userId: String): Flow<MechanicResponse>

    suspend fun getDetailPartOnWeb(userId: String): Flow<StorageResponse>

    suspend fun savedItemList(list: List<StorageEntity>)

    suspend fun clearTable()

    suspend fun getItemsStorage(): List<StorageEntity>

    suspend fun addSolicitud(serviceDTO: ServiceDTO): Flow<CommonResponse>

    suspend fun updateSolicitud(serviceDTO: ServiceDTO): Flow<CommonResponse>


}