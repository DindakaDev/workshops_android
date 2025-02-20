package com.dindaka.workshops_android.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dindaka.workshops_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onApply: (String, String, Pair<Long?, Long?>?, String) -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Todos") }
    var dates by remember { mutableStateOf<Pair<Long?, Long?>?>(null) }
    val order = remember { mutableStateOf("Ascendente") }

    val orders = listOf("Ascendente", "Descendente")

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { TitleTextComponent(stringResource(R.string.filtrar)) },
        text = {
            Column {
                NormalInput(name, placeHolder = R.string.solicitud) {
                    name = it
                }
                Spacer(modifier = Modifier.height(8.dp))
                StatusDropdown(selectedType) {
                    selectedType = it
                }
                Spacer(modifier = Modifier.height(8.dp))
                PickerDate(placeHolder = R.string.dates, rangeDate = dates) {
                    dates = it
                }
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.order), fontWeight = FontWeight.Bold)

                orders.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (order.value == option),
                                onClick = { order.value = option },
                                role = Role.RadioButton
                            )
                    ) {
                        RadioButton(
                            selected = (order.value == option),
                            onClick = { order.value = option })
                        Text(text = option, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onApply(
                    name,
                    selectedType,
                    dates,
                    order.value
                )
            }) {
                Text(stringResource(R.string.apply))
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { TitleTextComponent("Exito") },
        text = { Text("Proceso realizado con exito") },
        confirmButton = {
            onDismiss()
        }
    )
}