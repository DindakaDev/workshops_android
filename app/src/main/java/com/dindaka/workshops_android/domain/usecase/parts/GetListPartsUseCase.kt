package com.dindaka.workshops_android.domain.usecase.parts

import com.dindaka.workshops_android.data.local.db.entities.StorageEntity
import com.dindaka.workshops_android.domain.repository.WorkshopRepository

class GetListPartsUseCase(
    private val repository: WorkshopRepository,
) {
    suspend operator fun invoke():
            List<StorageEntity> =
        repository.getItemsStorage()
}