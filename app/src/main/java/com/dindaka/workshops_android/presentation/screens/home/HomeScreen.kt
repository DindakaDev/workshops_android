package com.dindaka.workshops_android.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.BodyTextComponent
import com.dindaka.workshops_android.presentation.components.FilterDialog
import com.dindaka.workshops_android.presentation.components.GeneralScaffold
import com.dindaka.workshops_android.presentation.navigation.Routes
import com.dindaka.workshops_android.presentation.screens.home.requested.HistoryServiceComponent
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController) {
    val courotineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val showFilterDialog = false
    ModalNavigationDrawer( //Drawer
        drawerState = drawerState,
        drawerContent = {
            DrawerGeneral(navController){
                courotineScope.launch {
                    drawerState.close()
                }
            }
        }
    ) {
        GeneralScaffold(
            title = R.string.menu,
            navigationIcon = {
                IconButton(onClick = {
                    courotineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Drawer")
                }
            },
            actions = {
                Row {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                }
            },
            container = {
                if(showFilterDialog) {
                    FilterDialog(onDismiss = {}) { name, type, dates, order ->

                    }
                }
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
                        ActionsComponent(navController)
                        Spacer(Modifier.size(8.dp))
                        HistoryServiceComponent(
                            title = "Historial de Servicios",
                            navController = navController
                        )
                    }
                    SyncParts()
                }
            }
        )
    }
}

@Composable
fun ActionsComponent(navController: NavHostController) {
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
                .clickable { navController.navigate(Routes.ApplyPart.route) }
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
            ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Nuevo servicio",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
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
                .clickable { navController.navigate(Routes.RequestedList.route) }
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