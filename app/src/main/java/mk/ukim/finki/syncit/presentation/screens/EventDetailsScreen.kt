@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.EventDetailsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.EventDetailsViewModelFactory
import mk.ukim.finki.syncit.utils.CategoryUtils
import mk.ukim.finki.syncit.utils.TopBarUtils
import mk.ukim.finki.syncit.utils.toFormattedDate

@Composable
fun EventDetailsScreen(
    eventId: String,
    authViewModel: AuthViewModel,
    eventRepository: EventRepository,
    navController: NavController
) {
    val viewModel: EventDetailsViewModel = viewModel(
        factory = EventDetailsViewModelFactory(eventId, eventRepository)
    )
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    val event by viewModel.event.collectAsState()

    if (event != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { TopBarUtils.CustomTitle(event!!.title) },
                    actions = { TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn) },
                    navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                    colors = TopBarUtils.CustomBackground(),
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            EventDetailsContent(event!!, Modifier.padding(innerPadding), navController, currentUser)
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { TopBarUtils.CustomTitle("SyncIt") },
                    actions = { TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn) },
                    navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                    colors = TopBarUtils.CustomBackground()
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Event not found", style = MaterialTheme.typography.headlineSmall)
            }
        }

    }
}

@Composable
fun EventDetailsContent(
    event: Event,
    modifier: Modifier = Modifier,
    navController: NavController,
    currentUser: User?
) {
    val imageUrl = CategoryUtils.getCategoryImage(event.category)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "${event.category} banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "📍 ${event.venue.title}, ${event.venue.address}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Date", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.startDateTimeParsed.toFormattedDate(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Host", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.host.fullName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AttachMoney, contentDescription = "Entry Fee", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (event.entryFee == 0L) "Free" else "${event.entryFee} MKD",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (currentUser != null) {
                Button(
                    onClick = { navController.navigate("buyTickets/${event.id}") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                ) {
                    Text("Buy Tickets", color = Color.White, fontSize = 16.sp)
                }

                if (currentUser.id == event.host.id) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("scanTickets") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
                    ) {
                        Text("Scan Tickets", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}