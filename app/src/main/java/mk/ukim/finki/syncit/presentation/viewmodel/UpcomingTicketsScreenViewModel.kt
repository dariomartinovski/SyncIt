package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.Ticket
import java.time.LocalDateTime

class UpcomingTicketsViewModel : ViewModel() {

    private val allTickets = MockData.tickets

    private val _selectedTab = MutableStateFlow(0) // 0 = Upcoming, 1 = History
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _filteredTickets = MutableStateFlow(getFilteredTickets(0))
    val filteredTickets: StateFlow<List<Ticket>> = _filteredTickets

    init {
        viewModelScope.launch {
            _selectedTab.collect { tab ->
                _filteredTickets.value = getFilteredTickets(tab)
            }
        }
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }

    private fun getFilteredTickets(tab: Int): List<Ticket> {
        return allTickets.filter {
            if (tab == 0) it.event.startTime.isAfter(LocalDateTime.now())
            else it.event.startTime.isBefore(LocalDateTime.now())
        }
    }
}