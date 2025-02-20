package com.dindaka.workshops_android.data.remote.dto.mechanic

import com.squareup.moshi.JsonClass

typealias MechanicResponse = Map<String, Mechanic>

@JsonClass(generateAdapter = true)
data class Mechanic(
    val nombre: String,
    val taller: String
)
