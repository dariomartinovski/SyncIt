package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.presentation.components.ShowErrorDialog
import mk.ukim.finki.syncit.presentation.components.ShowSuccessDialog
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.BuyTicketsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.BuyTicketsViewModelFactory
import mk.ukim.finki.syncit.utils.TopBarUtils
import mk.ukim.finki.syncit.utils.toSimpleFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyTicketsScreen(
    eventId: String,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val viewModel: BuyTicketsViewModel = viewModel(factory = BuyTicketsViewModelFactory(eventId))
    val event by viewModel.event
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    var quantity by remember { mutableStateOf(1) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showLoginRequiredDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    fun reserveTickets(user: User) {
        viewModel.quantity = quantity
        viewModel.reserveTickets(user) { success ->
            if (success) {
                showSuccessDialog = true
            } else {
                errorMessage = "Ticket reservation failed. Please try again."
                showErrorDialog = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Buy Tickets") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                colors = TopBarUtils.CustomBackground(),
            )
        },
        content = { innerPadding ->
            if (event == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Text(
                        text = event!!.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${event!!.startDateTimeParsed.toSimpleFormat()} - ${event!!.venue.title}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = quantity.toString(),
                        onValueChange = { newQuantity ->
                            quantity = newQuantity.toIntOrNull()?.coerceAtLeast(1) ?: 1
                        },
                        label = { Text("Quantity") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Total: \$${(event!!.entryFee) * quantity}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (isUserLoggedIn) {
                                if (currentUser != null) {
                                    reserveTickets(currentUser!!)
                                } else {
                                    errorMessage = "Could not get user data."
                                    showErrorDialog = true
                                }
                            } else {
                                showLoginRequiredDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                    ) {
                        Text("Confirm", color = Color.White)
                    }
                }
            }

            if (showSuccessDialog) {
                ShowSuccessDialog(
                    title = "Success",
                    message = "Tickets reserved successfully!",
                    onConfirm = {
                        showSuccessDialog = false
                        navController.popBackStack()
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
                    message = "You must be logged in to reserve tickets.",
                    onDismiss = { showLoginRequiredDialog = false }
                )
            }
        }
    )
}
