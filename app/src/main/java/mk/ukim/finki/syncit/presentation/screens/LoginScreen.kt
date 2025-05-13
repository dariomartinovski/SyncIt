package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.repository.AuthRepository
import mk.ukim.finki.syncit.presentation.viewmodel.LoginUiState
import mk.ukim.finki.syncit.presentation.viewmodel.LoginViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.LoginViewModelFactory
import mk.ukim.finki.syncit.utils.DarkBlueColor
import mk.ukim.finki.syncit.utils.DeepBlueColor
import mk.ukim.finki.syncit.utils.LightSilverColor
import mk.ukim.finki.syncit.utils.LightTextColor
import mk.ukim.finki.syncit.utils.SilverColor

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            AuthRepository(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderSection()
        LoginForm(navController, viewModel)
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepBlueColor)
            .padding(top = 125.dp, start = 50.dp, bottom = 50.dp)
        ,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Sign in to your\nAccount",
            fontSize = 40.sp,
            color = LightSilverColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Sign in to your Account",
            fontSize = 14.sp,
            color = SilverColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DeepBlueColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Login", color = LightTextColor, fontSize = 14.sp)
        }

        when (val state = loginState) {
            is LoginUiState.Loading -> Text("Logging in...")
            is LoginUiState.Error -> Text("Error: ${state.message}", color = Color.Red)
            is LoginUiState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Don't have an account? Register", color = DarkBlueColor, fontSize = 14.sp)
        }
    }
}
