package com.dindaka.workshops_android.presentation.screens.home.supply_part

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.data.remote.dto.service.GeolocalizacionDTO
import com.dindaka.workshops_android.data.remote.dto.service.HistorialDTO
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.TypeService
import com.dindaka.workshops_android.presentation.components.AutoCompleteTextField
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.components.GetCurrentLocation
import com.dindaka.workshops_android.presentation.components.ImageViewer
import com.dindaka.workshops_android.presentation.components.PrimaryButton
import com.dindaka.workshops_android.presentation.components.SuccessDialog
import com.dindaka.workshops_android.presentation.components.TitleTextComponent
import com.dindaka.workshops_android.utils.DateUtil

@Composable
fun SupplyPartScreen(
    navController: NavHostController,
    service: ServiceDTO,
    viewModel: SupplyPartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val error by viewModel.error.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isSuccess by viewModel.success.observeAsState(false)
    val isServicePending by viewModel.isServicePending.observeAsState(false)
    viewModel.checkServiceCurrentState(service)

    if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        viewModel.resetDialogs()
    }
    GeneralScaffold(
        title = if (service.tipo == TypeService.servicio.name) R.string.title_service_complete else R.string.title_supply_part,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            if (isServicePending) {
                IconButton(onClick = {
                    viewModel.onClickSend(service)
                }) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                }
            }
        },
        container = {
            if (isSuccess) {
                SuccessDialog {
                    viewModel.resetDialogs()
                    navController.popBackStack()
                }
            }
            GetCurrentLocation {
                viewModel.updateRequest {
                    copy(
                        geolocalizacionTaller = GeolocalizacionDTO(
                            latitud = it?.latitude,
                            longitud = it?.longitude
                        )
                    )
                }
            }
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    GeneralInformationRequest(service)
                    if (isServicePending) {
                        FormApplyPartComponent(Modifier.weight(1f), viewModel)
                    }
                    HistoryChangesComponents(Modifier.weight(1f), service)
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
fun HistoryChangesComponents(modifier: Modifier, service: ServiceDTO) {
    if (!service.historial.isNullOrEmpty()) {
        LazyColumn(modifier = modifier) {
            items(service.historial!!) {
                ItemHistoryComponent(it)
            }
        }
    }

}

@Composable
fun ItemHistoryComponent(history: HistorialDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = history.tallerName ?: "Taller desconocido",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = "Mecánico: ${history.mecanico ?: "No especificado"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Estatus: ${history.status ?: "Desconocido"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = "Localizacion: ${history.geolocalizacionTaller?.latitud ?: 0},${history.geolocalizacionTaller?.longitud ?: 0}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fecha: ${history.fecha ?: "No disponible"}",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


@Composable
fun GeneralInformationRequest(service: ServiceDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = service.name ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Pieza a surtir:", style = MaterialTheme.typography.labelMedium)
                Text(
                    service.piezaSolicitada ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            InfoRow(label = "Origen:", value = service.tallerSolicitante ?: "")
            InfoRow(label = "Solicitante:", value = service.mecanico ?: "")
            InfoRow(
                label = "Fecha:",
                value = DateUtil.convertUTCDateToLocal(service.fechaSolicitud ?: "")
            )
            InfoRow(
                label = "Estatus:",
                value = service.estatus ?: "",
                color = MaterialTheme.colorScheme.primary
            )
            InfoRow(
                label = "Localizacion:",
                value = "${service.geolocalizacionDTO?.latitud ?: 0},${service.geolocalizacionDTO?.longitud ?: 0}",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, color: Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(value, fontWeight = FontWeight.Bold, color = color)
    }
}


@Composable
fun FormApplyPartComponent(modifier: Modifier, viewModel: SupplyPartViewModel) {
    Column(modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        TitleTextComponent("Surtidor")
        Spacer(Modifier.size(8.dp))
        MechanicComponent(viewModel)
        Spacer(Modifier.size(8.dp))
        PrimaryButton(text = stringResource(R.string.lbl_fotografia)) { }
        Spacer(Modifier.size(20.dp))
        ImageAddedComponent()
    }
}

@Composable
fun MechanicComponent(viewModel: SupplyPartViewModel) {
    val mechanics by viewModel.mechanics.observeAsState()
    val history by viewModel.history.collectAsState()
    AutoCompleteTextField(
        history.mecanico ?: "",
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