package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import mk.ukim.finki.syncit.utils.toFormattedDate
import mk.ukim.finki.syncit.utils.toFormattedTime
import java.util.*

@Composable
fun DateTimePicker(
    selectedDate: Long?,
    selectedTime: Pair<Int, Int>?,
    onDateTimeSelected: (Long?, Pair<Int, Int>?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val formattedDateTime = selectedDate?.let { date ->
        val dateString = date.toFormattedDate()
        val timeString = selectedTime?.toFormattedTime() ?: "Select Time"
        "$dateString $timeString"
    } ?: ""

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
            onDateSelected = { date ->
                onDateTimeSelected(date, selectedTime)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    LaunchedEffect(selectedDate) {
        if (selectedDate != null) {
            showTimePicker = true
        }
    }

    if (showTimePicker) {
        TimePickerModal(
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
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
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
    onConfirm: (Pair<Int, Int>) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(timePickerState.hour to timePickerState.minute)
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
