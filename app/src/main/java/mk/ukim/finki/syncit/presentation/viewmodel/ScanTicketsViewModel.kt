package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.repository.TicketRepository

class ScanTicketViewModel(private val ticketRepository: TicketRepository) : ViewModel() {

    private val _hasCameraPermission = MutableStateFlow(false)
    val hasCameraPermission: StateFlow<Boolean> = _hasCameraPermission

    private val _showScanner = MutableStateFlow(false)
    val showScanner: StateFlow<Boolean> = _showScanner

    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult

    private val _isValidTicket = MutableStateFlow<Boolean?>(null)
    val isValidTicket: StateFlow<Boolean?> = _isValidTicket

    fun setCameraPermission(granted: Boolean) {
        _hasCameraPermission.value = granted
        _showScanner.value = granted
    }

    fun setScanResult(result: String) {
        _scanResult.value = result
        _showScanner.value = false
        validateTicket(result)
    }

    private fun validateTicket(code: String) {
        viewModelScope.launch {
            val tickets = ticketRepository.getAllTickets()
            _isValidTicket.value = tickets.any { it.uniqueCode == code }
        }
    }
}