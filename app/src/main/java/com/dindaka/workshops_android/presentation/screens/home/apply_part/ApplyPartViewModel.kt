package com.dindaka.workshops_android.presentation.screens.home.apply_part

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dindaka.workshops_android.data.local.db.entities.StorageEntity
import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import com.dindaka.workshops_android.data.remote.dto.mechanic.MechanicDTO
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.StatusService
import com.dindaka.workshops_android.data.remote.dto.service.TypeService
import com.dindaka.workshops_android.domain.usecase.mechanic.GetMechanicsServerUseCase
import com.dindaka.workshops_android.domain.usecase.parts.CheckPartExistOnStorageUseCase
import com.dindaka.workshops_android.domain.usecase.parts.GetListPartsUseCase
import com.dindaka.workshops_android.domain.usecase.service.UploadSolicitudUseCase
import com.dindaka.workshops_android.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyPartViewModel @Inject constructor(
    private val myPreferences: MyPreferences,
    private val getListParts: GetListPartsUseCase,
    private val checkPartExistOnStorageUseCase: CheckPartExistOnStorageUseCase,
    private val getMechanicsServerUseCase: GetMechanicsServerUseCase,
    private val uploadSolicitudUseCase: UploadSolicitudUseCase
) : ViewModel() {
    private val _localStorage = MutableLiveData<List<StorageEntity>>()
    val localStorage: LiveData<List<StorageEntity>> = _localStorage

    private val _mechanics = MutableLiveData<List<MechanicDTO>>()
    val mechanics: LiveData<List<MechanicDTO>> = _mechanics

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _firstValidationEachChanged = MutableLiveData<Boolean>()
    val firstValidationEachChanged: LiveData<Boolean> = _firstValidationEachChanged

    private val _partExist = MutableLiveData<Boolean?>()
    val partExist: LiveData<Boolean?> = _partExist

    private val _request = MutableStateFlow<ServiceDTO>(ServiceDTO(uid = null))
    val request = _request.asStateFlow()

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    init {
        getPiezas()
        getMechanics()
        initRequest()
    }

    private fun initRequest() {
        updateRequest {
            copy(
                tipo = TypeService.servicio.name,
                tallerSolicitante = myPreferences.getName(),
                tallerId = myPreferences.getUid()
            )
        }
    }

    private fun getPiezas() {
        viewModelScope.launch {
            _localStorage.value = getListParts()
        }
    }

    private fun getMechanics() {
        viewModelScope.launch {
            getMechanicsServerUseCase().collect {
                _mechanics.value = it
            }
        }
    }

    fun updateRequest(update: ServiceDTO.() -> ServiceDTO) {
        viewModelScope.launch {
            _request.value = _request.value.update()
        }
    }

    fun checkExistingProduct() {
        viewModelScope.launch {
            if(_request.value.piezaSolicitadaId == null){
                _partExist.value = null
                return@launch
            }
            checkPartExistOnStorageUseCase(request.value.piezaSolicitadaId ?: "").collect {
                disabledValidationButton()
                if (it) {
                    updateRequest { copy(tipo = TypeService.servicio.name) }
                } else {
                    updateRequest { copy(tipo = TypeService.solicitud.name) }
                }
                _partExist.value = it
            }
        }

    }

    fun checkSameValue(text: String) {
        if (text != request.value.piezaSolicitada && _localStorage.value?.filter { it.name == text } != null) {
            _firstValidationEachChanged.value = true
            updateRequest { copy(tipo = TypeService.servicio.name) }
            _partExist.value = null
        }
    }

    private fun disabledValidationButton() {
        _firstValidationEachChanged.value = false
    }

    fun findItemByIndex(index: Int): String? {
        return _localStorage.value?.getOrNull(index)?.uuid
    }

    fun onClickSend() {
        _isLoading.value = true
        if (_request.value.tipo == TypeService.servicio.name) {
            updateRequest { copy(estatus = StatusService.completado.name) }
        } else {
            updateRequest { copy(estatus = StatusService.pendiente.name) }
        }
        updateRequest { copy(fechaSolicitud = DateUtil.getCurrentDateUtc()) }
        viewModelScope.launch {
            uploadSolicitudUseCase(_request.value).collect {
                _isLoading.value = false
                if(it){
                    _success.value = true
                }else{
                    _error.value = "Error al guardar el registro intente mas tarde."
                }
            }
        }
    }

    fun resetDialogs() {
        _error.value = null
    }
}