package com.dindaka.workshops_android.domain.repository

import com.dindaka.workshops_android.data.remote.dto.login.LoginResponse

interface LoginRepository {
    suspend fun doLogin(userName: String, password: String, callback: (LoginResponse) -> Unit)
}