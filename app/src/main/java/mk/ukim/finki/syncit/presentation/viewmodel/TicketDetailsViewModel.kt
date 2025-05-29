package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Ticket
import mk.ukim.finki.syncit.data.repository.TicketRepository

class TicketDetailsViewModel(private val repository: TicketRepository) : ViewModel() {

    private val _ticket = MutableStateFlow<Ticket?>(null)
    val ticket: StateFlow<Ticket?> = _ticket

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadTicket(ticketId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val fetchedTicket = repository.getTicketById(ticketId)
            if (fetchedTicket != null) {
                _ticket.value = fetchedTicket
            } else {
                _error.value = "Ticket not found"
            }

            _isLoading.value = false
        }
    }
}
