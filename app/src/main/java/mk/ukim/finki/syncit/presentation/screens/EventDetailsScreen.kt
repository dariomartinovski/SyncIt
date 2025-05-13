@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.presentation.viewmodel.EventDetailsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.EventDetailsViewModelFactory
import mk.ukim.finki.syncit.utils.TopBarUtils

@Composable
fun EventDetailsScreen(
    eventId: String,
    navController: NavController
) {
    val viewModel: EventDetailsViewModel = viewModel(
        factory = EventDetailsViewModelFactory(eventId)
    )

    val event by viewModel.event.collectAsState()

    if (event != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { TopBarUtils.CustomTitle(event!!.title) },
                    navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                    colors = TopBarUtils.CustomBackground()
                )
            }
        ) { innerPadding ->
            EventDetailsContent(event!!, Modifier.padding(innerPadding), navController)
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Event not found", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun EventDetailsContent(event: Event, modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Light Blue Background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = event.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = event.description, fontSize = 16.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "📍 Venue: ${event.venue.title}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "📅 Start Time: ${event.startTime}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "👥 Participants: ${event.participants.size} attending",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Buy Tickets Button
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                navController.navigate("buyTickets/${event.id}")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)) // Blue Button
        ) {
            Text(
                text = "Buy Tickets",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        //TODO add the scan tickets button here.
        Button(
            onClick = {
                navController.navigate("scanTickets")
            }
        ) {
            Text(
                text = "Scan tickets",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}