package mk.ukim.finki.syncit.presentation.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mk.ukim.finki.syncit.presentation.components.ShowErrorDialog
import mk.ukim.finki.syncit.presentation.components.ShowSuccessDialog
import mk.ukim.finki.syncit.presentation.viewmodel.ScanTicketViewModel

@Composable
fun ScanTicketScreen(navController: NavController, viewModel: ScanTicketViewModel = viewModel()) {
    val context = LocalContext.current

    val showScanner by viewModel.showScanner.collectAsState()
    val scanResult by viewModel.scanResult.collectAsState()
    val hasCameraPermission by viewModel.hasCameraPermission.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.setCameraPermission(true)
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
                onCodeScanned = { scannedCode -> viewModel.setScanResult(scannedCode) },
                onClose = { navController.navigate("home") }
            )
        } else {
            scanResult?.let { code ->
                val isValid = viewModel.isValidTicket(code)
                if (isValid) {
                    ShowSuccessDialog("Valid Ticket", "The ticket is valid.") {
                        navController.navigate("home")
                    }
                } else {
                    ShowErrorDialog("Invalid Ticket", "This ticket does not exist.") {
                        navController.navigate("home")
                    }
                }
            }
        }
    }
}