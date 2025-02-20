package com.dindaka.workshops_android.domain.usecase.login

import com.dindaka.workshops_android.data.remote.dto.login.LoginResponse
import com.dindaka.workshops_android.domain.repository.LoginRepository

class LoginUseCase (private val repository: LoginRepository){

    suspend operator fun invoke(user: String, password: String, callback: (LoginResponse) -> Unit) {
        repository.doLogin(user, password) {
            callback(it)
        }
    }
}