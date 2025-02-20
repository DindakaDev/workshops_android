package com.dindaka.workshops_android.data.remote.dto.login


data class LoginResponse(
    val status: Boolean,
    val uid: String?,
    val email: String?,
    val error: String?
)