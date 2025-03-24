package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.EventList
import mk.ukim.finki.syncit.presentation.components.SegmentedToggle
import mk.ukim.finki.syncit.presentation.components.TicketList
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingEventsScreen(navController: NavController) {
    var createdTab by remember { mutableStateOf(0) } // 0 = Upcoming, 1 = History
    var ticketsTab by remember { mutableStateOf(0) } // 0 = Upcoming, 1 = History

    //TODO here
    val userId = "current_user_id" // Replace with actual user ID retrieval
    //TODO make the calls
//    val createdEvents = MockData.events.filter { it.host.id == userId }
//    val ticketsEvents = MockData.events.filter { it.participants.any { p -> p == userId } }
    val createdEvents = MockData.events.take(5)
    val tickets = MockData.tickets

    val createdFiltered = createdEvents.filter {
        if (createdTab == 0) it.startTime.isAfter(LocalDateTime.now())
        else it.startTime.isBefore(LocalDateTime.now())
    }

    val ticketsFiltered = tickets.filter {
        if (ticketsTab == 0) it.event.startTime.isAfter(LocalDateTime.now())
        else it.event.startTime.isBefore(LocalDateTime.now())
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Events") }) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Section for Created Events
            Column(modifier = Modifier.weight(1f, false)) {
                Text("My Created Events", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SegmentedToggle(
                        options = listOf("Upcoming", "History"),
                        selectedIndex = createdTab,
                        onOptionSelected = { createdTab = it }
                    )
                }
                Spacer(Modifier.height(10.dp))
                EventList(events = createdFiltered, navController = navController)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section for Tickets
            Column(modifier = Modifier.weight(1f, false)) {
                Text("My Tickets", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SegmentedToggle(
                        options = listOf("Upcoming", "History"),
                        selectedIndex = ticketsTab,
                        onOptionSelected = { ticketsTab = it }
                    )
                }
                Spacer(Modifier.height(10.dp))
                TicketList(tickets = ticketsFiltered, navController = navController)
            }
        }
    }
}

