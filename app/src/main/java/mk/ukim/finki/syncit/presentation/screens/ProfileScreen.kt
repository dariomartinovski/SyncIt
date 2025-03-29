package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.model.UserModel
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.utils.TopBarUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var user by remember {
        mutableStateOf(
            UserModel(
                id = "1",
                firstName = "John",
                lastName = "Doe",
                phone = "123-456-7890",
                email = "johndoe@example.com",
                password = ""
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Profile") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
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
            Text("Edit Profile", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // First Name
            OutlinedTextField(
                value = user.firstName,
                onValueChange = { user = user.copy(firstName = it) },
                label = { Text("First Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Last Name
            OutlinedTextField(
                value = user.lastName,
                onValueChange = { user = user.copy(lastName = it) },
                label = { Text("Last Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone
            OutlinedTextField(
                value = user.phone,
                onValueChange = { user = user.copy(phone = it) },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email (Read-only)
            OutlinedTextField(
                value = user.email,
                onValueChange = {},
                label = { Text("Email") },
                readOnly = true,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = { println("Saved: $user") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}