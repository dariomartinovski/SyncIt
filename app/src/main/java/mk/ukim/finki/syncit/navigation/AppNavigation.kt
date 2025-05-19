package mk.ukim.finki.syncit.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.repository.AuthRepository
import mk.ukim.finki.syncit.presentation.screens.AddEventScreen
import mk.ukim.finki.syncit.presentation.screens.AddVenueScreen
import mk.ukim.finki.syncit.presentation.screens.BuyTicketsScreen
import mk.ukim.finki.syncit.presentation.screens.EventDetailsScreen
import mk.ukim.finki.syncit.presentation.screens.HomeScreen
import mk.ukim.finki.syncit.presentation.screens.LoginScreen
import mk.ukim.finki.syncit.presentation.screens.LogoutScreen
import mk.ukim.finki.syncit.presentation.screens.ProfileScreen
import mk.ukim.finki.syncit.presentation.screens.RegisterScreen
import mk.ukim.finki.syncit.presentation.screens.ScanTicketScreen
import mk.ukim.finki.syncit.presentation.screens.TicketDetailsScreen
import mk.ukim.finki.syncit.presentation.screens.UpcomingEventsScreen
import mk.ukim.finki.syncit.presentation.screens.UpcomingTicketsScreen
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.AuthViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(
            AuthRepository(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
        )
    )

    NavHost(navController = navController, startDestination = "home") {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController, authViewModel) }
        composable("logout") { LogoutScreen(navController, authViewModel) }
        composable("upcomingEvents") { UpcomingEventsScreen(navController, authViewModel) }
        composable("upcomingTickets") { UpcomingTicketsScreen(navController, authViewModel) }
        composable("profile") { ProfileScreen(navController, authViewModel) }
        composable("eventDetails/{eventId}") { backStackEntry ->
            EventDetailsScreen(
                eventId = backStackEntry.arguments?.getString("eventId") ?: "",
                navController = navController
            )
        }
        composable("ticketDetails/{ticketId}") { backStackEntry ->
            TicketDetailsScreen(
                ticketId = backStackEntry.arguments?.getString("ticketId") ?: "",
                navController = navController
            )
        }
//        composable("profile/{userId}") { backStackEntry ->
//            ProfileScreen(userId = backStackEntry.arguments?.getString("userId") ?: "")
//        }
        composable("addVenue") { AddVenueScreen(navController, authViewModel) }
        composable("addEvent") { AddEventScreen(navController, authViewModel) }
        composable("buyTickets/{eventId}") { backStackEntry ->
            BuyTicketsScreen(
                eventId = backStackEntry.arguments?.getString("eventId") ?: "",
                navController = navController
            )
        }
        composable("scanTickets") { ScanTicketScreen(navController) }
    }
}
