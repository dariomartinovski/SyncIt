package mk.ukim.finki.syncit.presentation.screens

import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.repository.AuthRepository
import mk.ukim.finki.syncit.presentation.viewmodel.RegisterState
import mk.ukim.finki.syncit.presentation.viewmodel.RegisterViewModel
import mk.ukim.finki.syncit.utils.DarkBlueColor
import mk.ukim.finki.syncit.utils.DeepBlueColor
import mk.ukim.finki.syncit.utils.LightSilverColor
import mk.ukim.finki.syncit.utils.SilverColor
import mk.ukim.finki.syncit.utils.ValidationUtils

@Composable
fun RegisterScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val viewModel: RegisterViewModel = viewModel {
        RegisterViewModel(
            AuthRepository(
                FirebaseAuth.getInstance(),
                FirebaseFirestore.getInstance()
            )
        )
    }

    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Success -> {
                Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
                navController.navigate("login")
            }
            is RegisterState.Error -> {
                Toast.makeText(context, "Error while trying to register", Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        RegisterHeader()
        RegisterForm(navController, viewModel)
    }
}

@Composable
fun RegisterHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepBlueColor)
            .padding(top = 125.dp, start = 50.dp, bottom = 50.dp)
    ) {
        Text(
            text = "Register for an\nAccount",
            fontSize = 40.sp,
            color = LightSilverColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Register for an Account",
            fontSize = 14.sp,
            color = SilverColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RegisterForm(navController: NavController, viewModel: RegisterViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var firstNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordErrors by remember { mutableStateOf(emptyList<String>()) }

    fun clearErrors() {
        firstNameError = ""
        lastNameError = ""
        phoneNumberError = ""
        emailError = ""
        passwordErrors = emptyList()
    }

    fun validateAndRegister() {
        clearErrors()
        if (!ValidationUtils.isValidName(firstName)) {
            firstNameError = "Name must contain only letters"
        }
        if (!ValidationUtils.isValidName(lastName)) {
            lastNameError = "Last name must contain only letters"
        }
        phoneNumberError = ValidationUtils.isValidPhoneNumber(phoneNumber) ?: ""
        if (!ValidationUtils.isValidEmail(email)) {
            emailError = "Email is not valid"
        }
        passwordErrors = ValidationUtils.isValidPassword(password)

        if (firstNameError.isEmpty() && lastNameError.isEmpty() &&
            phoneNumberError.isEmpty() && emailError.isEmpty() &&
            passwordErrors.isEmpty()
        ) {
            viewModel.register(firstName, lastName, phoneNumber, email, password)
        }
    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(firstNameError)

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(lastNameError)

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(phoneNumberError)

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(emailError)

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        passwordErrors.forEach { ErrorText(it) }

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { validateAndRegister() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DeepBlueColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Already have an account? Login", color = DarkBlueColor)
            }
        }
    }
}

@Composable
fun ErrorText(error: String) {
    if (error.isNotEmpty()) {
        Spacer(Modifier.height(3.dp))
        Text(error, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}