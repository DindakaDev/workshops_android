package com.dindaka.workshops_android.data.remote.dto.service

import android.os.Parcelable
import com.dindaka.workshops_android.network.NullToEmptyString
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


typealias ServicesResponse = Map<String, ServiceDTO>

@JsonClass(generateAdapter = true)
@Parcelize
data class ServiceDTO(
    @NullToEmptyString var uid: String?,
    val name: String? = null,
    val tipo: String? = null,
    var estatus: String? = null,
    val fechaSolicitud: String? = null,
    val mecanico: String? = null,
    val piezaSolicitadaId: String? = null,
    val piezaSolicitada: String? = null,
    val tallerSolicitante: String? = null,
    val tallerId: String? = null,
    val vinVehiculo: String? = null,
    var historial: List<HistorialDTO>? = null,
    val geolocalizacionDTO: GeolocalizacionDTO? = null,

    val fotografiaEvidencia: String? = null,
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class GeolocalizacionDTO(
    val latitud: Double? = null,
    val longitud: Double? = null
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class HistorialDTO(
    val tallerId: String? = null,
    val tallerName: String? = null,
    val mecanico: String? = null,
    val fecha: String? = null,
    var status: String? = null,

    val evidencia: String? = null,
    val geolocalizacionTaller: GeolocalizacionDTO ?= null,
): Parcelable
