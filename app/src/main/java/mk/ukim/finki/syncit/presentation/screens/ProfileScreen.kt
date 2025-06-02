package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.remote.UserService
import mk.ukim.finki.syncit.data.repository.UserRepository
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.ShowErrorDialog
import mk.ukim.finki.syncit.presentation.components.ShowSuccessDialog
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.ProfileViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.ProfileViewModelFactory
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    val db = remember { FirebaseFirestore.getInstance() }
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(UserRepository(UserService(db)))
    )

    val userState by viewModel.user.collectAsState()

    LaunchedEffect(currentUser?.id) {
        currentUser?.id?.let { viewModel.loadUser(it) }
    }

    // New dialog states
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Profile") },
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
                TextUtils.LargeTitle("Edit Profile")

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = userState.firstName,
                    onValueChange = { viewModel.updateUser(userState.copy(firstName = it)) {} },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = userState.lastName,
                    onValueChange = { viewModel.updateUser(userState.copy(lastName = it)) {} },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = userState.phoneNumber,
                    onValueChange = { viewModel.updateUser(userState.copy(phoneNumber = it)) {} },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = userState.email,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.updateUser(userState) { success ->
                            if (success) {
                                showSuccessDialog = true
                            } else {
                                errorMessage = "Failed to save changes."
                                showErrorDialog = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        else
            TextUtils.CenteredMessage(
                message = "Please log in to view your events.",
                modifier = Modifier.padding(innerPadding)
            )

            if (showSuccessDialog) {
                ShowSuccessDialog(
                    title = "Success",
                    message = "Changes saved successfully!",
                    onConfirm = { showSuccessDialog = false }
                )
            }

            if (showErrorDialog) {
                ShowErrorDialog(
                    title = "Error",
                    message = errorMessage,
                    onDismiss = { showErrorDialog = false }
                )
            }
    }
}