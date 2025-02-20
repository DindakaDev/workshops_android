package com.dindaka.workshops_android.domain.usecase.parts

import com.dindaka.workshops_android.data.local.db.entities.StorageEntity
import com.dindaka.workshops_android.data.remote.dto.parts.StorageResponse
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStorageFromServerUseCase(
    private val repository: WorkshopRepository,
) {
    suspend operator fun invoke(): Flow<StorageResponse> =
        repository.getStorage().map { it ->
            repository.clearTable()
            repository.savedItemList(it.map {
                StorageEntity(
                    name = it.value.nombre,
                    descripcion = it.value.descripcion,
                    uuid = it.key
                )
            })
            it
        }
}
