@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.*
import mk.ukim.finki.syncit.data.remote.TicketService
import mk.ukim.finki.syncit.data.repository.TicketRepository
import mk.ukim.finki.syncit.presentation.viewmodel.TicketDetailsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.TicketDetailsViewModelFactory
import mk.ukim.finki.syncit.utils.QRCodeGenerator
import mk.ukim.finki.syncit.utils.TopBarUtils

@Composable
fun TicketDetailsScreen(
    ticketId: String,
    navController: NavController,
    viewModel: TicketDetailsViewModel = viewModel(
        factory = TicketDetailsViewModelFactory(
            TicketRepository(
                TicketService(FirebaseFirestore.getInstance())
            )
        )
    ),
) {
    val ticket by viewModel.ticket.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(ticketId) {
        viewModel.loadTicket(ticketId)
    }

    LaunchedEffect(ticket?.uniqueCode) {
        ticket?.uniqueCode?.let {
            qrBitmap = QRCodeGenerator.generateQrCode(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Ticket Details") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                colors = TopBarUtils.CustomBackground(),
            )
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(error ?: "Unknown error", color = Color.Red, fontSize = 20.sp)
                }
            }

            ticket != null -> {
                TicketDetailsContent(
                    ticket = ticket!!,
                    qrBitmap = qrBitmap,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun TicketDetailsContent(ticket: Ticket, qrBitmap: Bitmap?, modifier: Modifier = Modifier) {
    val event = ticket.event
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimaryContainer

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = primaryColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = onPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Venue", tint = primaryColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Venue: ${event.venue.title}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Start Time", tint = primaryColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Start Time: ${event.startTime}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Participants", tint = primaryColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Participants: ${event.participants.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Category", tint = primaryColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Category: ${event.category.label}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Client", tint = primaryColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Client: ${ticket.user.firstName} ${ticket.user.lastName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Scan for Entry",
            style = MaterialTheme.typography.titleMedium,
            color = primaryColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        qrBitmap?.let { bitmap ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "QR Code for Ticket",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}