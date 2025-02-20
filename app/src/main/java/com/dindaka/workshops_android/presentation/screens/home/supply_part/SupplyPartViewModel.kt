package com.dindaka.workshops_android.presentation.screens.home.supply_part

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.dto.mechanic.MechanicDTO
import com.dindaka.workshops_android.data.remote.dto.service.HistorialDTO
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.StatusService
import com.dindaka.workshops_android.data.remote.dto.service.TypeService
import com.dindaka.workshops_android.domain.usecase.mechanic.GetMechanicsServerUseCase
import com.dindaka.workshops_android.domain.usecase.service.UpdateSolicitudUseCase
import com.dindaka.workshops_android.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupplyPartViewModel @Inject constructor(
    private val getMechanicsServerUseCase: GetMechanicsServerUseCase,
    private val myPreferences: MyPreferences,
    private val updateSolicitudUseCase: UpdateSolicitudUseCase
) : ViewModel() {
    private val _mechanics = MutableLiveData<List<MechanicDTO>>()
    val mechanics: LiveData<List<MechanicDTO>> = _mechanics

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _history = MutableStateFlow<HistorialDTO>(HistorialDTO())
    val history = _history.asStateFlow()

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _isServicePending = MutableLiveData<Boolean>()
    val isServicePending: LiveData<Boolean> = _isServicePending

    init {
        initRequest()
        getMechanics()
    }

    private fun initRequest() {
        updateRequest {
            copy(
                status = StatusService.enviado.name,
                tallerName = myPreferences.getName(),
                tallerId = myPreferences.getUid()
            )
        }
    }

    fun checkServiceCurrentState(service: ServiceDTO) {
        _isServicePending.value = service.tipo == TypeService.solicitud.name && service.estatus == StatusService.pendiente.name && service.tallerId != myPreferences.getUid()
    }

    private fun getMechanics() {
        viewModelScope.launch {
            getMechanicsServerUseCase().collect {
                _mechanics.value = it
            }
        }
    }

    fun updateRequest(update: HistorialDTO.() -> HistorialDTO) {
        viewModelScope.launch {
            _history.value = _history.value.update()
        }
    }

    fun onClickSend(serviceDTO: ServiceDTO) {
        _isLoading.value = true
        updateRequest { copy(fecha = DateUtil.getCurrentDateUtc()) }
        if (serviceDTO.historial == null) {
            serviceDTO.historial = emptyList()
        }
        serviceDTO.estatus = StatusService.enviado.name
        val list = serviceDTO.historial?.toMutableList()
        list?.add(history.value)
        serviceDTO.historial = list?.toList()
        viewModelScope.launch {
            updateSolicitudUseCase(serviceDTO).collect {
                _isLoading.value = false
                if (it) {
                    _success.value = true
                } else {
                    _error.value = "Error al actualizar el registro intente mas tarde."
                }
            }
        }
    }

    fun resetDialogs() {
        _error.value = null
        _success.value = false
    }
}