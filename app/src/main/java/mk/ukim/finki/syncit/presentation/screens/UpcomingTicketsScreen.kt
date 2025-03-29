package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.SegmentedToggle
import mk.ukim.finki.syncit.presentation.components.TicketList
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingTicketsScreen(navController: NavController) {
    var ticketsTab by remember { mutableStateOf(0) } // 0 = Upcoming, 1 = History

    val userId = "current_user_id" // Replace with actual user ID retrieval
    //TODO make the call to fetch user tickets
    val tickets = MockData.tickets

    val ticketsFiltered = tickets.filter {
        if (ticketsTab == 0) it.event.startTime.isAfter(LocalDateTime.now())
        else it.event.startTime.isBefore(LocalDateTime.now())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("My Tickets") },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController) },
                colors = TopBarUtils.CustomBackground(),
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TextUtils.LargeTitle("My Tickets")

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