package com.dindaka.workshops_android.presentation.screens.home.requested

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dindaka.workshops_android.presentation.navigation.Routes

@Composable
fun HistoryServiceComponent(filter: String? = null, title: String? = null, navController: NavController? = null) {
    val historyItems =
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 1)
    Column(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if(title != null) {
            Text(
                title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.size(16.dp))
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(historyItems) {
                ItemHistory(Modifier.clickable { navController?.navigate(Routes.SupplyPart.route) })
            }
        }
    }
}

@Composable
fun ItemHistory(modifier: Modifier) {
    Column(modifier){
        Text("Nombre solicitud")
        Row {
            Text("2024-12-10 09-00-00", fontSize = 12.sp, modifier = Modifier.weight(1f))
            Text("Nuevo", fontSize = 12.sp)
        }
        HorizontalDivider()
    }
}