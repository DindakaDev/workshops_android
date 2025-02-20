package com.dindaka.workshops_android.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pieza(
    val nombre: String,
    val cantidad: Int?,
    val descripcion: String
)
