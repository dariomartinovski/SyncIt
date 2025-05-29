package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Ticket
import mk.ukim.finki.syncit.data.repository.TicketRepository
import java.time.LocalDateTime

class UpcomingTicketsViewModel(
    private val repository: TicketRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _allTickets = MutableStateFlow<List<Ticket>>(emptyList())
    private val _filteredTickets = MutableStateFlow<List<Ticket>>(emptyList())
    val filteredTickets: StateFlow<List<Ticket>> = _filteredTickets

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            _selectedTab.collect { tab ->
                _filteredTickets.value = filterTickets(tab, _allTickets.value)
            }
        }
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }

    fun loadTicketsForUser(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val tickets = repository.getTicketsForUser(userId)
                _allTickets.value = tickets
                _filteredTickets.value = filterTickets(_selectedTab.value, tickets)
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            }

            _isLoading.value = false
        }
    }

    private fun filterTickets(tab: Int, tickets: List<Ticket>): List<Ticket> {
        val now = LocalDateTime.now()
        return tickets.filter {
            val eventDate = it.event.startDateTimeParsed
            if (tab == 0) eventDate.isAfter(now) else eventDate.isBefore(now)
        }
    }
}