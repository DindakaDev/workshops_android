package com.dindaka.workshops_android.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dindaka.workshops_android.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PickerDate(
    @StringRes placeHolder: Int,
    rangeDate: Pair<Long?, Long?>?,
    onSelectedDate: (Pair<Long?, Long?>?) -> Unit
) {
    var showPicker by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = "${rangeDate?.first?.let { formatDate(it) } ?: ""} - ${
            rangeDate?.second?.let {
                formatDate(
                    it
                )
            } ?: ""
        }",
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(placeHolder)) },
        trailingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Seleccionar fecha",
                Modifier.clickable {
                    showPicker = true
                })
        },
        modifier = Modifier
            .fillMaxWidth()
    )
    if (showPicker) {
        DateRangePickerModal(onDismiss = { showPicker = false }, onDateRangeSelected = { pair ->
            showPicker = false
            onSelectedDate.invoke(pair)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.apply))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = stringResource(R.string.select_date_range)
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

// Función para formatear fecha como String
fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
