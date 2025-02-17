package com.dindaka.workshops_android.domain.usecase.login

import com.dindaka.workshops_android.domain.repository.LoginRepository

class LoginUseCase (private val repository: LoginRepository){

    suspend operator fun invoke(user: String, password: String): Boolean {
        return repository.doLogin(user, password)
    }
}