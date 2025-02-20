package com.dindaka.workshops_android.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dindaka.workshops_android.R
import com.dindaka.workshops_android.presentation.components.TitleTextComponent

@Composable
fun DrawerGeneral(name: String, logout: () -> Unit,  actionDrawer: () -> Unit) {
    ModalDrawerSheet {
        Column(modifier = Modifier.padding(8.dp)) {
            DrawerHead(name)
            //DrawerItem(stringResource(R.string.lbl_edit), icon = Icons.Filled.Edit, onClick = {})
            DrawerItem(
                stringResource(R.string.lbl_logout),
                icon = Icons.AutoMirrored.Filled.Logout,
                onClick = {
                    logout()
                })
        }
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text)
        Text(
            text, modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun DrawerHead(name: String) {
    Column {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            "Talleres Unidos",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp
        )
        Spacer(Modifier.size(16.dp))
        TitleTextComponent(name)
    }
}