package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.remote.TicketService
import mk.ukim.finki.syncit.data.repository.TicketRepository
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.SegmentedToggle
import mk.ukim.finki.syncit.presentation.components.TicketList
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.UpcomingTicketsViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.UpcomingTicketsViewModelFactory
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingTicketsScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    val context = LocalContext.current
    val firestore = remember { FirebaseFirestore.getInstance() }
    val ticketService = remember { TicketService(firestore) }
    val ticketRepository = remember { TicketRepository(ticketService) }
    val viewModel: UpcomingTicketsViewModel = viewModel(
        factory = UpcomingTicketsViewModelFactory(ticketRepository)
    )

    val selectedTab by viewModel.selectedTab.collectAsState()
    val tickets by viewModel.filteredTickets.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            viewModel.loadTicketsForUser(it.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("My Tickets") },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn) },
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
                    selectedIndex = selectedTab,
                    onOptionSelected = { viewModel.onTabSelected(it) }
                )
            }

            Spacer(Modifier.height(10.dp))

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    }
                }

                else -> {
                    TicketList(tickets = tickets, navController = navController)
                }
            }
        }
    }
}