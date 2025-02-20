package com.dindaka.workshops_android.presentation.screens.home.requested

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.presentation.navigation.Routes
import com.dindaka.workshops_android.utils.DateUtil

@Composable
fun HistoryServiceComponent(
    title: String? = null,
    navController: NavController? = null,
    items: List<ServiceDTO>
) {
    Column(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (title != null) {
            Text(
                title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.size(16.dp))
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) {
                ItemHistory(
                    Modifier.clickable {
                        navController?.currentBackStackEntry?.savedStateHandle?.set("service", it)
                        navController?.navigate(Routes.SupplyPart.route)},
                    service = it
                )
            }
        }
    }
}

@Composable
fun ItemHistory(modifier: Modifier, service: ServiceDTO) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = service.piezaSolicitada ?: stringResource(R.string.unknown),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = "Tipo: ${service.tipo ?: "Desconocido"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Estatus: ${service.estatus ?: "Desconocido"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fecha: ${DateUtil.convertUTCDateToLocal(service.fechaSolicitud ?: "")}",
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic
            )
        }
    }


}