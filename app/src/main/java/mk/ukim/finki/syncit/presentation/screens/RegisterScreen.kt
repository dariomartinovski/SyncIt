package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mk.ukim.finki.syncit.utils.ValidationUtils

@Composable
fun RegisterScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
        RegisterHeader()
        RegisterForm(navController)
    }
}

@Composable
fun RegisterHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF003366))
            .padding(top = 125.dp, start = 50.dp, bottom = 50.dp)
    ) {
        Text(
            text = "Register for an\nAccount",
            fontSize = 40.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Register for an Account",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RegisterForm(navController: NavController) {
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

    fun _clearErrors() {
        firstNameError = ""
        lastNameError = ""
        phoneNumberError = ""
        emailError = ""
        passwordErrors = emptyList()
    }

    fun _login() {
        _clearErrors()
        if (!ValidationUtils.isValidName(firstName)) {
            firstNameError = "Name must contain only letters"
        }
        if (!ValidationUtils.isValidName(lastName)) {
            lastNameError = "Last name must contain only letters"
        }
        phoneNumberError = ValidationUtils.isValidPhoneNumber(phoneNumber) ?: ""
        if (!ValidationUtils.isValidEmail(email)) {
            emailError = "Last name must contain only letters"
        }
        passwordErrors = ValidationUtils.isValidPassword(password)

        if (firstNameError.isNotEmpty() || lastNameError.isNotEmpty() ||
            phoneNumberError.isNotEmpty() || emailError.isNotEmpty() ||
            passwordErrors.isNotEmpty())
            return

        //TODO make call here
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            ) {
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
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordErrors.isNotEmpty()) {
            Column {
                for (error in passwordErrors) {
                    ErrorText(error)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                _login()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF003366)
            ),
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
                Text("Already have an account? Login")
            }
        }

    }
}

@Composable
fun ErrorText(error: String) {
    if (error.isNotEmpty()){
        Spacer(Modifier.height(3.dp))
        Text(error, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}
