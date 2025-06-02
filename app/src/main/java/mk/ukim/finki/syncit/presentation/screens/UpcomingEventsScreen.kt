package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.remote.EventService
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.EventList
import mk.ukim.finki.syncit.presentation.components.SegmentedToggle
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.UpcomingEventsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.UpcomingEventsViewModelFactory
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingEventsScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    val firestore = remember { FirebaseFirestore.getInstance() }
    val eventService = remember { EventService(firestore) }
    val eventRepository = remember { EventRepository(eventService) }
    val viewModel: UpcomingEventsViewModel = viewModel(
        factory = UpcomingEventsViewModelFactory(eventRepository)
    )

    LaunchedEffect(currentUser) {
        currentUser?.id?.let { userId ->
            viewModel.fetchEvents(userId)
        }
    }

    val createdTab by viewModel.createdTab.collectAsState()
    val filteredEvents by viewModel.filteredEvents.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("My Events") },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn) },
                colors = TopBarUtils.CustomBackground(),
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        if (isUserLoggedIn)
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
                        onOptionSelected = { viewModel.setCreatedTab(it) }
                    )
                }

                Spacer(Modifier.height(10.dp))
                EventList(events = filteredEvents, navController = navController)
            }
        else
            TextUtils.CenteredMessage(
                message = "Please log in to view your events.",
                modifier = Modifier.padding(innerPadding)
            )
    }
}