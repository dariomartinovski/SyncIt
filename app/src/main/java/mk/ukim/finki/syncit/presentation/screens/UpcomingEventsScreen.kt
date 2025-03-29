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
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingEventsScreen(navController: NavController) {
    var createdTab by remember { mutableStateOf(0) } // 0 = Upcoming, 1 = History

    val userId = "current_user_id" // Replace with actual user ID retrieval
    //TODO make the call to fetch user-created events
    val createdEvents = MockData.events.take(5)

    val createdFiltered = createdEvents.filter {
        if (createdTab == 0) it.startTime.isAfter(LocalDateTime.now())
        else it.startTime.isBefore(LocalDateTime.now())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("My Events") },
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
            TextUtils.LargeTitle("My Created Events")

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
    }
}