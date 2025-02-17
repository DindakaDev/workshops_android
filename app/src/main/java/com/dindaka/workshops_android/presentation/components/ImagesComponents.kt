package com.dindaka.workshops_android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.dindaka.workshops_android.presentation.theme.Workshops_androidTheme

@Composable
fun ImageViewer(
    imageUrl: String?,
    onDelete: () -> Unit
) {
    var isFullScreen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .size(150.dp)
                .clickable { isFullScreen = true },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "No image",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Gray
                )
            }
        }
    }

    if (isFullScreen && imageUrl != null) {
        FullScreenImage(imageUrl, onClose = { isFullScreen = false }, onDelete = onDelete)
    }
}

@Composable
fun FullScreenImage(imageUrl: String, onClose: () -> Unit, onDelete: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagen en pantalla completa",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar imagen", tint = Color.Red)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewImage() {
    Workshops_androidTheme {
        ImageViewer("https://imgs.search.brave.com/lrPg7R5LklwlmlDG8svGBAqjBQFDs_B1Nn1WCrTUiEo/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly91cGxv/YWQud2lraW1lZGlh/Lm9yZy93aWtpcGVk/aWEvY29tbW9ucy85/Lzk0LzA5MTIyNV90/YW1hbGVzLkpQRw") { }
    }
}
