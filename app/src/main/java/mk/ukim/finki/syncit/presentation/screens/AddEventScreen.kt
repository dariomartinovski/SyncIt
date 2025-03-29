@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.enums.Category
import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.presentation.components.DateTimePicker
import mk.ukim.finki.syncit.utils.TopBarUtils
import mk.ukim.finki.syncit.utils.toFormattedDate
import mk.ukim.finki.syncit.utils.toFormattedTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var entryFee by remember { mutableStateOf("") }
    var selectedVenue by remember { mutableStateOf<Venue?>(null) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedTime by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var expandedVenue by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var expandedCategory by remember { mutableStateOf(false) }

    fun _saveEvent() {
        println(title)
        println(description)
        println(selectedVenue)
        println(entryFee)
        println(selectedDate?.toFormattedDate())
        println(selectedTime?.toFormattedTime())
        println(selectedCategory)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Add Event") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController) },
                colors = TopBarUtils.CustomBackground(),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = entryFee,
                onValueChange = { entryFee = it },
                label = { Text("Entry Fee") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            DateTimePicker(
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onDateTimeSelected = { date, time ->
                    selectedDate = date
                    selectedTime = time
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedVenue,
                onExpandedChange = { expandedVenue = !expandedVenue }
            ) {
                OutlinedTextField(
                    value = selectedVenue?.title ?: "Select Venue",
                    onValueChange = {},
                    label = { Text("Select Venue") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expandedVenue,
                    onDismissRequest = { expandedVenue = false }
                ) {
                    MockData.venues.forEach { venue ->
                        DropdownMenuItem(
                            text = { Text(venue.title) },
                            onClick = {
                                selectedVenue = venue
                                expandedVenue = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = !expandedCategory }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.label ?: "Select Category",
                    onValueChange = {},
                    label = { Text("Select Category") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }
                ) {
                    Category.entries.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.label) },
                            onClick = {
                                selectedCategory = category
                                expandedCategory = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { _saveEvent() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Event")
            }
        }
    }
}

