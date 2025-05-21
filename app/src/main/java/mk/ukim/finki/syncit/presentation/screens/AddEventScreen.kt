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
import mk.ukim.finki.syncit.data.model.enums.Category
import mk.ukim.finki.syncit.presentation.components.DateTimePicker
import mk.ukim.finki.syncit.presentation.viewmodel.AddEventViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import mk.ukim.finki.syncit.presentation.components.ShowErrorDialog
import mk.ukim.finki.syncit.presentation.components.ShowSuccessDialog
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
    val currentUser by authViewModel.currentUser.collectAsState()

    var showLoginRequiredDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
                    viewModel.venues.forEach { venue ->
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
                onClick = {
                    if (isUserLoggedIn) {
                        println("Current User is")
                        println(currentUser)
                        if (currentUser != null) {
                            viewModel.saveEvent(currentUser!!) { success, error ->
                                if (success) {
                                    showSuccessDialog = true
                                } else {
                                    errorMessage = error ?: "An unexpected error occurred."
                                    showErrorDialog = true
                                }
                            }
                        } else {
                            // Optional: show loading or retry logic
                            println("******************")
                            println("ERROR??")
                            println("******************")
                        }
                    } else {
                        showLoginRequiredDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Event")
            }


//            Button(
//                onClick = {
//                    if (isUserLoggedIn) {
//                        authViewModel.currentUser?.let { user ->
//                            viewModel.saveEvent(user) { success, error ->
//                                if (success) {
//                                    showSuccessDialog = true
//                                } else {
//                                    errorMessage = error ?: "An unexpected error occurred."
//                                    showErrorDialog = true
//                                }
//                            }
//                        } ?: run {
//                            errorMessage = "Unable to retrieve user data."
//                            showErrorDialog = true
//                        }

//                        if (currentUserId != null) {
//                            viewModel.saveEvent(currentUserId!!) { success, error ->
//                                if (success) {
//                                    showSuccessDialog = true
//                                } else {
//                                    errorMessage = error ?: "An unexpected error occurred."
//                                    showErrorDialog = true
//                                }
//                            }
//                        } else {
//                            println("SANJOOOO")
//                            println(isUserLoggedIn)
//                            println(currentUserId)
//                            // Optional: show a loading indicator or disable button
//                        }
//                    } else {
//                        showLoginRequiredDialog = true
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Save Event")
//            }
        }

        if (showSuccessDialog) {
            ShowSuccessDialog(
                title = "Success",
                message = "Event has been saved successfully!",
                onConfirm = {
                    showSuccessDialog = false
                    navController.navigate("home") {
                        popUpTo("addEvent") { inclusive = true }
                    }
                }
            )
        }

        if (showErrorDialog) {
            ShowErrorDialog(
                title = "Error",
                message = errorMessage,
                onDismiss = { showErrorDialog = false }
            )
        }

        if (showLoginRequiredDialog) {
            ShowErrorDialog(
                title = "Login Required",
                message = "You need to be logged in to create an event.",
                onDismiss = { showLoginRequiredDialog = false }
            )
        }
    }
}

