package com.dindaka.workshops_android.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.StatusService
import com.dindaka.workshops_android.data.remote.dto.service.TypeService
import com.dindaka.workshops_android.domain.usecase.parts.GetStorageFromServerUseCase
import com.dindaka.workshops_android.domain.usecase.service.GetServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val myPreferences: MyPreferences,
    private val getStorageFromServerUseCase: GetStorageFromServerUseCase,
    private val getServicesUseCase: GetServicesUseCase
) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showFilterDialog = MutableLiveData<Boolean>()
    val showFilterDialog: LiveData<Boolean> = _showFilterDialog

    private val _historyService = MutableLiveData<List<ServiceDTO>>()
    val historyService: LiveData<List<ServiceDTO>> = _historyService

    private val _countPendingService = MutableLiveData<Int>()
    val countPendingService: LiveData<Int> = _countPendingService

    init {
        getName()
        getHistory()
    }

    fun getHistory() {
        viewModelScope.launch {
            _isLoading.value = false
            getServicesUseCase(null, null).collect {
                _countPendingService.value =
                    it.filter { element -> element.tipo == TypeService.solicitud.name && element.estatus == StatusService.pendiente.name && element.tallerId != myPreferences.getUid() }.size
                _historyService.value = it
            }
        }
    }

    private fun getName() {
        _name.value = myPreferences.getName()
    }

    fun onUserSyncStorage() {
        viewModelScope.launch {
            _isLoading.value = true
            viewModelScope.launch {
                getStorageFromServerUseCase().collect {
                    _isLoading.value = false
                    getHistory()
                }

            }
        }
    }

    fun showOrHideFilterDialog() {
        _showFilterDialog.value = !(_showFilterDialog.value ?: false)
    }

    fun clearLocalData() {
        myPreferences.clearPreferences()
    }
}