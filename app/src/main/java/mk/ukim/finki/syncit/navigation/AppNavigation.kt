package mk.ukim.finki.syncit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mk.ukim.finki.syncit.presentation.screens.AddEventScreen
import mk.ukim.finki.syncit.presentation.screens.AddVenueScreen
import mk.ukim.finki.syncit.presentation.screens.BuyTicketsScreen
import mk.ukim.finki.syncit.presentation.screens.EventDetailsScreen
import mk.ukim.finki.syncit.presentation.screens.HomeScreen
import mk.ukim.finki.syncit.presentation.screens.ProfileScreen
import mk.ukim.finki.syncit.presentation.screens.TicketDetailsScreen
import mk.ukim.finki.syncit.presentation.screens.UpcomingEventsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("upcomingEvents") { UpcomingEventsScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
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
        composable("addVenue") { AddVenueScreen(navController) }
        composable("addEvent") { AddEventScreen(navController) }
        composable("buyTickets/{eventId}") { backStackEntry ->
            BuyTicketsScreen(
                eventId = backStackEntry.arguments?.getString("eventId") ?: "",
                navController = navController
        )
        }
    }
}
