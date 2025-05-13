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
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.*
import mk.ukim.finki.syncit.utils.QRCodeGenerator
import mk.ukim.finki.syncit.utils.TopBarUtils

@Composable
fun TicketDetailsScreen(ticketId: String, navController: NavController) {
    val ticket = MockData.tickets.find { it.id == ticketId }

    val qrGenerator = remember { QRCodeGenerator() }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    if (ticket?.uniqueCode != null) {
        LaunchedEffect(ticket.uniqueCode) {
            qrBitmap = qrGenerator.generateQrCode(ticket.uniqueCode)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Ticket Details") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController) },
                colors = TopBarUtils.CustomBackground(),
            )
        },
        content = { innerPadding ->
            ticket?.let {
                TicketDetailsContent(
                    ticket = it,
                    qrBitmap = qrBitmap,
                    modifier = Modifier.padding(innerPadding)
                )
            } ?: run {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Ticket not found", fontSize = 20.sp, color = Color.Red)
                }
            }
        }
    )
}

@Composable
fun TicketDetailsContent(ticket: Ticket, qrBitmap: Bitmap?, modifier: Modifier = Modifier) {
    val event = ticket.event

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Use the modifier passed from Scaffold
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ticket Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Light Blue Background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = event.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1) // Dark Blue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.description,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Venue", tint = Color(0xFF0D47A1))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Venue: ${event.venue.title}",
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Start Time", tint = Color(0xFF0D47A1)) // Calendar icon
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Start Time: ${event.startTime}",
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Participants", tint = Color(0xFF0D47A1)) // People icon
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Participants: ${event.participants.size}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Category", tint = Color(0xFF0D47A1)) // Category icon
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Category: ${event.category.label}",
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "User", tint = Color(0xFF0D47A1))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "User: ${ticket.user.firstName} ${ticket.user.lastName}",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // QR Code Section
        Text("Scan for Entry", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
        Spacer(modifier = Modifier.height(8.dp))
        qrBitmap?.let { bitmap ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(8.dp), // Adds a white border around the QR
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