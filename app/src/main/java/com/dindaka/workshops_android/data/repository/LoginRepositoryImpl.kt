package com.dindaka.workshops_android.data.repository

import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.ApiService
import com.dindaka.workshops_android.data.remote.dto.login.LoginResponse
import com.dindaka.workshops_android.domain.repository.LoginRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val myPreferences: MyPreferences
) :
    LoginRepository {
    override suspend fun doLogin(
        userName: String,
        password: String,
        callback: (LoginResponse) -> Unit
    ) {
        try {
            Firebase.auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        myPreferences.setUid(result.result.user?.uid)
                        myPreferences.setEmail(userName)
                        callback(
                            LoginResponse(
                                status = true,
                                uid = result.result.user?.uid,
                                email = userName,
                                error = null
                            )
                        )
                    } else {
                        callback(
                            LoginResponse(
                                status = false,
                                uid = null,
                                email = userName,
                                error = "Datos de acceso incorrectos"
                            )
                        )
                    }
                }
        } catch (e: Exception) {
            callback(
                LoginResponse(
                    status = false,
                    uid = null,
                    email = userName,
                    error = "Intente de vuelta mas tarde"
                )
            )
        }
    }
}