package com.dindaka.workshops_android.domain.repository

interface LoginRepository {
    suspend fun doLogin(userName: String, password: String): Boolean
}