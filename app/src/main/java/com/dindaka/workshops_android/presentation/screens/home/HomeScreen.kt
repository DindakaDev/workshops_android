package com.dindaka.workshops_android.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.BodyTextComponent
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.components.TitleTextComponent
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme

@Composable
fun HomeScreen() {
    GeneralScaffold(
        title = R.string.menu,
        actions = {
            Row{
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Menu, contentDescription = "Drawer")
            }
        },
        container = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    ActionsComponent()
                    Spacer(Modifier.size(8.dp))
                    HistoryServiceComponent()
                }
                SyncParts()
            }
        }
    )
}

@Composable
fun ActionsComponent() {
    Row(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        ) {
            Column(Modifier.align(Alignment.Center)) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Nuevo servicio",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    "Nuevo Servicio",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(Modifier.size(8.dp))
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Column(Modifier.align(Alignment.Center)) {
                Text(
                    "Solicitud piezas",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    "8",
                    fontSize = 29.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HistoryServiceComponent() {
    val historyItems =
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 1)
    Column(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Historial de Servicios",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.size(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(historyItems) {
                ItemHistory()
            }
        }
    }
}

@Composable
fun ItemHistory() {
    Column {
        Text("Nombre solicitud")
        Row {
            Text("2024-12-10 09-00-00", fontSize = 12.sp, modifier = Modifier.weight(1f))
            Text("Nuevo", fontSize = 12.sp)
        }
        HorizontalDivider()
    }
}

@Composable
fun SyncParts() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_sync),
                contentDescription = "Sync",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterHorizontally)
            )
            BodyTextComponent("Sync", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewHome() {
    Workshops_androidTheme {
        HomeScreen()
    }
}
