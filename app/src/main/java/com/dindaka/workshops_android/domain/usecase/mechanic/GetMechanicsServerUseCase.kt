package com.dindaka.workshops_android.domain.usecase.mechanic

import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.dto.mechanic.MechanicDTO
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMechanicsServerUseCase(
    private val repository: WorkshopRepository,
    private val myPreferences: MyPreferences
) {
    suspend operator fun invoke(): Flow<List<MechanicDTO>> =
        repository.getMechanic(myPreferences.getUid() ?: "").map { it ->
            val list = mutableListOf<MechanicDTO>()
            it.map {
                list.add(
                    MechanicDTO(
                        nombre = it.value.nombre,
                        taller = it.value.taller,
                        uid = it.key
                    )
                )
            }
            list.toList()
        }
}
