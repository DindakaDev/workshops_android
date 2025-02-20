package com.dindaka.workshops_android.domain.usecase.parts

import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckPartExistOnStorageUseCase(
    private val repository: WorkshopRepository,
    private val myPreferences: MyPreferences,
) {
    suspend operator fun invoke(uidPart: String): Flow<Boolean> =
        repository.getDetailPartOnWeb(myPreferences.getUid() ?: "").map {
            it.containsKey(uidPart) && (it[uidPart]?.cantidad ?: 0) > 0
        }
}