package com.dindaka.workshops_android.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TitleTextComponent(text: String, modifier: Modifier = Modifier.fillMaxWidth()) {
    Text(
        text,
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun BodyTextComponent(text: String, modifier: Modifier = Modifier.fillMaxWidth(), textAlign: TextAlign = TextAlign.Center) {
    Text(
        text,
        textAlign = textAlign,
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}