package com.dindaka.workshops_android.presentation.screens.home.apply_part

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.components.ImageViewer
import com.dindaka.workshops_android.presentation.components.LinkButton
import com.dindaka.workshops_android.presentation.components.NormalInput
import com.dindaka.workshops_android.presentation.components.PrimaryButton
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme

@Composable
fun ApplyPartScreen(navController: NavHostController) {
    GeneralScaffold(
        title = R.string.title_install, //R.string.title_solicitud_pieza,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        },
        container = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                FormApplyPartComponent()
            }
        }
    )
}

@Composable
fun FormApplyPartComponent() {
    val onlyIfPieceExist: Boolean? = null
    // Cada cambio en las piezas habilita el boton para validar existencia con true
    val firstValidationEachChanged = false
    Column(Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        NormalInput("", placeHolder = R.string.lbl_solicitud) {}
        Spacer(Modifier.size(8.dp))
        NormalInput("", placeHolder = R.string.lbl_pieza) {}
        Spacer(Modifier.size(8.dp))
        if (firstValidationEachChanged) {
            LinkButton(text = stringResource(R.string.btn_check_existence)) { }
        }
        if (onlyIfPieceExist == null) {
            Box {}
        } else if (onlyIfPieceExist) {
            // Las opciones de abajo solo las mostrare si existe la pieza
            PrimaryButton(text = stringResource(R.string.lbl_fotografia)) { }
            Spacer(Modifier.size(20.dp))
            ImageAddedComponent()
        } else {
            FormToSuppyPart()
        }
    }
}

@Composable
fun FormToSuppyPart() {
    Column {
        NormalInput("", placeHolder = R.string.vin_vehicle) {}
        Spacer(Modifier.size(8.dp))
        NormalInput("", placeHolder = R.string.name_mecanico) {}
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