package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel

@Composable
fun LogoutScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    LaunchedEffect(Unit) {
        authViewModel.logout()
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
        }
    }
}