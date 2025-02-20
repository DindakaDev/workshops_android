package com.dindaka.workshops_android.presentation.screens.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dindaka.workshops_android.domain.usecase.login.LoginUseCase
import com.dindaka.workshops_android.domain.usecase.workshop.GetDataWorkshopUseCase
import com.dindaka.workshops_android.domain.usecase.parts.GetStorageFromServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getDataWorkshopUseCase: GetDataWorkshopUseCase,
    private val getStorageFromServerUseCase: GetStorageFromServerUseCase
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun onLoginChange(username: String, password: String) {
        _username.value = username
        _password.value = password

        _isLoginEnabled.value = isValidData(username, password)
    }

    private fun isValidData(username: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(username).matches() && password.length >= 6

    fun onLoginSelected() {
        viewModelScope.launch {
            _isLoading.value = true
            loginUseCase(username.value!!, password.value!!) { result ->
                if (result.status) {
                    viewModelScope.launch {
                        getDataWorkshopUseCase().collect {
                            getStorageFromServerUseCase().collect{
                                _navigateToHome.value = true
                                _isLoading.value = false
                            }
                        }
                    }
                } else {
                    _error.value = result.error ?: ""
                    _isLoading.value = false
                }
            }
        }
    }

    fun resetShowError() {
        _error.value = null
    }

    fun onNavigated() {
        _navigateToHome.value = false
    }
}