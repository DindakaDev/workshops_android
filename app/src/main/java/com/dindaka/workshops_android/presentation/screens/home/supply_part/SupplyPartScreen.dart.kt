package com.dindaka.workshops_android.presentation.screens.home.supply_part

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.components.ImageViewer
import com.dindaka.workshops_android.presentation.components.NormalInput
import com.dindaka.workshops_android.presentation.components.PrimaryButton
import com.dindaka.workshops_android.presentation.components.TitleTextComponent
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme

@Composable
fun SupplyPartScreen(navController: NavHostController) {
    GeneralScaffold(
        title = R.string.title_supply_part,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        },
        container = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                GeneralInformationRequest()
                FormApplyPartComponent(Modifier.weight(1f))
            }
        }
    )
}

@Composable
fun GeneralInformationRequest() {
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
                text = "Surtir pieza X",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Pieza a surtir:", style = MaterialTheme.typography.labelMedium)
                Text(
                    "Martillo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            InfoRow(label = "Origen:", value = "Taller Dorado")
            InfoRow(label = "Solicitante:", value = "Jorge Campos")
            InfoRow(label = "Fecha:", value = "12-Mayo-2024 09:00:00")
            InfoRow(label = "Estatus:", value = "Activo", color = MaterialTheme.colorScheme.primary)
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
fun FormApplyPartComponent(modifier: Modifier) {
    Column(modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        TitleTextComponent("Surtidor")
        Spacer(Modifier.size(8.dp))
        NormalInput("", placeHolder = R.string.name_mecanico) {}
        Spacer(Modifier.size(8.dp))
        PrimaryButton(text = stringResource(R.string.lbl_fotografia)) { }
        Spacer(Modifier.size(20.dp))
        ImageAddedComponent()
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