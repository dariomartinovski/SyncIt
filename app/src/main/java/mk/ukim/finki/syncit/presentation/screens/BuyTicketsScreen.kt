package mk.ukim.finki.syncit.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mk.ukim.finki.syncit.presentation.viewmodel.BuyTicketsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.BuyTicketsViewModelFactory
import mk.ukim.finki.syncit.utils.TopBarUtils
import mk.ukim.finki.syncit.utils.toSimpleFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyTicketsScreen(
    eventId: String,
    navController: NavController
) {
    val viewModel: BuyTicketsViewModel = viewModel(factory = BuyTicketsViewModelFactory(eventId))
    val event = viewModel.event

    var quantity by remember { mutableStateOf(viewModel.quantity) }

    fun reserveTickets() {
        // Print the quantity of tickets in the console (or handle further actions)
        println("Reserved $quantity tickets for ${event?.title}")
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                Text(
                    text = event?.title ?: "Event not found",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${event?.startTime?.toSimpleFormat()} - ${event?.venue?.title}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quantity.toString(),
                    onValueChange = { newQuantity ->
                        quantity = newQuantity.toIntOrNull() ?: 1
                    },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total: \$${viewModel.totalAmount}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        reserveTickets()
                        // Optionally, navigate back or show a confirmation message
                        Toast.makeText(navController.context, "Tickets Reserved", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)) // Blue Button
                ) {
                    Text(text = "Confirm", color = Color.White)
                }
            }
        }
    )
}
