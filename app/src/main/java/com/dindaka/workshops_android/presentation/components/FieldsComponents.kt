package com.dindaka.workshops_android.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun PasswordInput(password: String, @StringRes placeHolder: Int, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(placeHolder)) },
        maxLines = 1,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = PasswordVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )
}

@Composable
fun NormalInput(text: String, @StringRes placeHolder: Int, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(placeHolder)) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )
}