package mk.ukim.finki.syncit.presentation.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.presentation.components.ShowErrorDialog
import mk.ukim.finki.syncit.presentation.components.ShowSuccessDialog

@Composable
fun ScanTicketScreen(navController: NavController) {
    var showScanner by remember { mutableStateOf(false) }
    var scanResult by remember { mutableStateOf<String?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                hasCameraPermission = true
                showScanner = true
            } else {
                Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showScanner && hasCameraPermission) {
            TicketScannerScreen(
                onCodeScanned = { scannedCode ->
                    scanResult = scannedCode
                    showScanner = false
                },
                onClose = {
                    navController.navigate("home")
                }
            )
        } else {
            scanResult?.let { code ->
                ValidateTicket(code)
            }
        }
    }
}

@Composable
fun ValidateTicket(ticketCode: String) {
    val isValid = MockData.tickets.any { it.uniqueCode == ticketCode }
    if (isValid) {
        ShowSuccessDialog("Valid Ticket", "The ticket is valid.") { /* Handle success */ }
    } else {
        ShowErrorDialog("Invalid Ticket", "This ticket does not exist.") { /* Handle error */ }
    }
}
