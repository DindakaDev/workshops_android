package com.dindaka.workshops_android.data.repository

import com.dindaka.workshops_android.domain.repository.LoginRepository


class LoginRepositoryImpl: LoginRepository {
    override suspend fun doLogin(userName: String, password: String): Boolean {
        TODO("Not yet implemented")
    }
}