package com.dindaka.workshops_android.presentation.components

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import com.dindaka.workshops_android.R
import java.io.File

@Composable
fun CameraCapture(
    onImageCaptured: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val photoUri = remember { mutableStateOf<Uri?>(null) }
    val photoUriPreview = remember { mutableStateOf<Uri?>(null) }

    fun createImageFile(): Uri {
        val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyApp")
        if (!storageDir.exists()) storageDir.mkdirs()

        val file = File(storageDir, "IMG_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri.value?.let { onImageCaptured(it) }
            photoUriPreview.value = photoUri.value
        }
    }

    fun capturePhoto() {
        val uri = createImageFile()
        photoUri.value = uri
        takePictureLauncher.launch(uri)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            capturePhoto()
        } else {
            Toast.makeText(context, "Permiso de cámara requerido", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        PrimaryButton(text = stringResource(R.string.lbl_fotografia)) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
        Spacer(Modifier.size(20.dp))
        photoUriPreview.value?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Imagen Capturada",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
