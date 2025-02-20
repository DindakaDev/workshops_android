package com.dindaka.workshops_android.domain.usecase.workshop

import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.dto.workshop.WorkshopsDataResponse
import com.dindaka.workshops_android.domain.repository.WorkshopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDataWorkshopUseCase(
    private val repository: WorkshopRepository,
    private val myPreferences: MyPreferences,
) {
    suspend operator fun invoke(): Flow<WorkshopsDataResponse> =
        repository.getDataUser(myPreferences.getUid() ?: "").map {
            myPreferences.setName(it.nombre)
            myPreferences.setLat(it.geolocalizacion.latitud.toFloat())
            myPreferences.setLng(it.geolocalizacion.longitud.toFloat())
            it
        }
}