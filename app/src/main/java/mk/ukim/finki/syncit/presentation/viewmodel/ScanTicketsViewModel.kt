package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mk.ukim.finki.syncit.data.mock.MockData

class ScanTicketViewModel : ViewModel() {
    private val _hasCameraPermission = MutableStateFlow(false)
    val hasCameraPermission: StateFlow<Boolean> = _hasCameraPermission

    private val _showScanner = MutableStateFlow(false)
    val showScanner: StateFlow<Boolean> = _showScanner

    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult

    fun setCameraPermission(granted: Boolean) {
        _hasCameraPermission.value = granted
        _showScanner.value = granted
    }

    fun setScanResult(result: String) {
        _scanResult.value = result
        _showScanner.value = false
    }

    fun isValidTicket(code: String): Boolean {
        return MockData.tickets.any { it.uniqueCode == code }
    }
}
