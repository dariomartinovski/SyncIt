package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
//import androidx.compose.material.icons.filled.AddCircle
//import androidx.compose.material.icons.filled.Close
import mk.ukim.finki.syncit.utils.LightTextColor
import mk.ukim.finki.syncit.utils.WarningColor

@Composable
fun ShowErrorDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    //Error icon should be here
                    imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = null,
                    tint = WarningColor,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = WarningColor)
                ) {
                    Text("OK", color = LightTextColor)
                }
            }
        }
    }
}

fun getFirebaseErrorTitle(code: String): String {
    return when (code) {
        "user-not-found" -> "User Not Found"
        "wrong-password" -> "Incorrect Password"
        "invalid-email" -> "Invalid Email Format"
        "email-already-in-use" -> "Email Already In Use"
        "weak-password" -> "Weak Password"
        "too-many-requests" -> "Too Many Attempts"
        else -> "Firebase Error"
    }
}