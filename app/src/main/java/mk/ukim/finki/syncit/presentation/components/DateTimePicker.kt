package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.time.*
import mk.ukim.finki.syncit.utils.toFormattedDate
import mk.ukim.finki.syncit.utils.toFormattedTime

@Composable
fun DateTimePicker(
    selectedDate: LocalDate,
    selectedTime: LocalTime,
    onDateTimeSelected: (LocalDate, LocalTime) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val formattedDateTime = "${selectedDate.toFormattedDate()} ${selectedTime.toFormattedTime()}"

    OutlinedTextField(
        value = formattedDateTime,
        onValueChange = { },
        label = { Text("Select Date & Time") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select date & time")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDatePicker) {
        DatePickerModal(
            initialDate = selectedDate,
            onDateSelected = { date ->
                onDateTimeSelected(date, selectedTime)
                showDatePicker = false
                showTimePicker = true
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }

    if (showTimePicker) {
        TimePickerModal(
            initialTime = selectedTime,
            onConfirm = { time ->
                onDateTimeSelected(selectedDate, time)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val zoneId = ZoneId.systemDefault()
    val initialMillis = initialDate.atStartOfDay(zoneId).toInstant().toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis,
        initialDisplayMode = DisplayMode.Picker
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val millis = datePickerState.selectedDateMillis
                val selected = millis?.let {
                    Instant.ofEpochMilli(it).atZone(zoneId).toLocalDate()
                }
                if (selected != null) {
                    onDateSelected(selected)
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    initialTime: LocalTime,
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                onConfirm(selectedTime)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Select Time") },
        text = {
            Column {
                TimePicker(state = timePickerState)
            }
        }
    )
}