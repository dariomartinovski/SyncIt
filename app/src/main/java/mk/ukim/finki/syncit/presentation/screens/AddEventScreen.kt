@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.enums.Category
import mk.ukim.finki.syncit.presentation.components.DateTimePicker
import mk.ukim.finki.syncit.presentation.viewmodel.AddEventViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    viewModel: AddEventViewModel = viewModel()
) {
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedVenue by remember { mutableStateOf(false) }

    val title = viewModel.title
    val description = viewModel.description
    val entryFee = viewModel.entryFee
    val selectedVenue = viewModel.selectedVenue
    val selectedDate = viewModel.selectedDate
    val selectedTime = viewModel.selectedTime
    val selectedCategory = viewModel.selectedCategory

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Add Event") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn) },
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
            TextUtils.LargeTitle("Add Event")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.title = it },
                label = { Text("Event Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = entryFee,
                onValueChange = { viewModel.entryFee = it },
                label = { Text("Entry Fee") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            DateTimePicker(
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onDateTimeSelected = { date, time ->
                    viewModel.selectedDate = date
                    viewModel.selectedTime = time
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
                                viewModel.selectedVenue = venue
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
                                viewModel.selectedCategory = category
                                expandedCategory = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { viewModel.saveEvent() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Event")
            }
        }
    }
}

