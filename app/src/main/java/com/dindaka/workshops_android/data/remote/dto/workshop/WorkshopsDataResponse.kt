package com.dindaka.workshops_android.data.remote.dto.workshop

import com.dindaka.workshops_android.data.remote.dto.common.Pieza
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkshopsDataResponse(
    val nombre: String,
    val geolocalizacion: Geolocalizacion,
    val inventario: Map<String, Pieza>
)

@JsonClass(generateAdapter = true)
data class Geolocalizacion(
    val latitud: Double,
    val longitud: Double
)