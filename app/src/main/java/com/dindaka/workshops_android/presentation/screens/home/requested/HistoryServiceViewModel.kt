package com.dindaka.workshops_android.presentation.screens.home.requested

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.StatusService
import com.dindaka.workshops_android.data.remote.dto.service.TypeService
import com.dindaka.workshops_android.domain.usecase.service.GetServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryServiceViewModel @Inject constructor(
    private val getServiceUseCase: GetServicesUseCase
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _historyService = MutableLiveData<List<ServiceDTO>>()
    val historyService: LiveData<List<ServiceDTO>> = _historyService

    init {
        getHistory()
    }

    fun getHistory() {
        viewModelScope.launch {
            _isLoading.value = false
            getServiceUseCase(TypeService.solicitud, StatusService.pendiente).collect {
                _historyService.value = it
            }
        }
    }
}