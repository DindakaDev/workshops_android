package com.dindaka.workshops_android.presentation.screens.home.apply_part

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.AutoCompleteTextField
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.components.ImageViewer
import com.dindaka.workshops_android.presentation.components.LinkButton
import com.dindaka.workshops_android.presentation.components.NormalInput
import com.dindaka.workshops_android.presentation.components.PrimaryButton
import com.dindaka.workshops_android.presentation.components.SuccessDialog

@Composable
fun ApplyPartScreen(
    navController: NavHostController,
    viewModel: ApplyPartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val error by viewModel.error.observeAsState(null)
    val partExist by viewModel.partExist.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isSuccess by viewModel.success.observeAsState(false)


    if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        viewModel.resetDialogs()
    }
    GeneralScaffold(
        title = if ((partExist != false)) R.string.title_install else R.string.title_solicitud_pieza,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                viewModel.onClickSend()
            }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        },
        container = {
            Box {
                if(isSuccess){
                    SuccessDialog {
                        viewModel.resetDialogs()
                        navController.popBackStack()
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    FormApplyPartComponent(viewModel)
                }
                if (isLoading) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable { }) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    )
}

@Composable
fun FormApplyPartComponent(viewModel: ApplyPartViewModel) {
    val request by viewModel.request.collectAsState()
    val firstValidationEachChanged by viewModel.firstValidationEachChanged.observeAsState(false)
    val partExist by viewModel.partExist.observeAsState()
    val items by viewModel.localStorage.observeAsState()

    // Cada cambio en las piezas habilita el boton para validar existencia con true
    Column(Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        NormalInput(request.name ?: "", placeHolder = R.string.lbl_solicitud) {
            viewModel.updateRequest { copy(name = it) }
        }
        Spacer(Modifier.size(8.dp))
        AutoCompleteTextField(
            request.piezaSolicitada ?: "",
            placeHolder = R.string.lbl_pieza,
            suggestions = items?.map { it.name }?.toList() ?: emptyList()
        ) { index, text ->
            viewModel.checkSameValue(text)
            viewModel.updateRequest {
                copy(
                    piezaSolicitada = text,
                    piezaSolicitadaId = viewModel.findItemByIndex(index)
                )
            }
        }
        Spacer(Modifier.size(8.dp))
        if (firstValidationEachChanged) {
            LinkButton(text = stringResource(R.string.btn_check_existence)) {
                viewModel.checkExistingProduct()
            }
        }
        if (partExist == null) {
            Box {}
        } else if (partExist!!) {
            MechanicComponent(viewModel)
            PrimaryButton(text = stringResource(R.string.lbl_fotografia)) { }
            Spacer(Modifier.size(20.dp))
            ImageAddedComponent()
        } else {
            FormToSuppyPart(viewModel)
        }
    }
}

@Composable
fun FormToSuppyPart(viewModel: ApplyPartViewModel) {
    val request by viewModel.request.collectAsState()
    Column {
        NormalInput(request.vinVehiculo ?: "", placeHolder = R.string.vin_vehicle) {
            viewModel.updateRequest { copy(vinVehiculo = it) }
        }
        Spacer(Modifier.size(8.dp))
        MechanicComponent(viewModel)
    }
}

@Composable
fun MechanicComponent(viewModel: ApplyPartViewModel) {
    val mechanics by viewModel.mechanics.observeAsState()
    val request by viewModel.request.collectAsState()
    AutoCompleteTextField(
        request.mecanico ?: "",
        placeHolder = R.string.name_mecanico,
        suggestions = mechanics?.map { it.nombre }?.toList() ?: emptyList()
    ) { _, text ->
        viewModel.updateRequest {
            copy(
                mecanico = text,
            )
        }
    }
}

@Composable
fun ImageAddedComponent() {
    val listImages = listOf(
        "https://img.freepik.com/foto-gratis/tocar-mano-icono-busqueda-optimizacion-motor-busqueda-o-concepto-seo-encontrar-informacion-conexion-internet_616485-37.jpg?t=st=1739689176~exp=1739692776~hmac=f16a1be2f17852751988436d7924f18f220cdc54839c6e468b890bea44e97155&w=2000",
        "https://img.freepik.com/foto-gratis/tocar-mano-icono-busqueda-optimizacion-motor-busqueda-o-concepto-seo-encontrar-informacion-conexion-internet_616485-37.jpg?t=st=1739689176~exp=1739692776~hmac=f16a1be2f17852751988436d7924f18f220cdc54839c6e468b890bea44e97155&w=2000"
    )
    LazyRow(Modifier.fillMaxWidth()) {
        items(listImages) {
            ImageViewer(it) { }
            Spacer(Modifier.size(5.dp))
        }
    }
}