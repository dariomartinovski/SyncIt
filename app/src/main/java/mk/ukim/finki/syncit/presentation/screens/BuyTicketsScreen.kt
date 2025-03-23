package mk.ukim.finki.syncit.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.utils.toSimpleFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyTicketsScreen(eventId: String, navController: NavController) {
    val event = MockData.events.find { it.id == eventId }

    // State for the quantity of tickets
    var quantity by remember { mutableStateOf(1) }

    // State for the total amount
    val totalAmount = event?.entryFee?.times(quantity) ?: 0

    // Function to simulate ticket reservation
    fun reserveTickets() {
        // Print the quantity of tickets in the console (or handle further actions)
        println("Reserved $quantity tickets for ${event?.title}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buy Tickets") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
                    text = "Total: \$${totalAmount}",
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
