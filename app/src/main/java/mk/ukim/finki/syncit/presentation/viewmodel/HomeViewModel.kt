package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.repository.EventRepository
import java.time.LocalDateTime

class HomeViewModel(private val repository: EventRepository) : ViewModel() {

    private val _allEvents = mutableStateOf<List<Event>>(emptyList())
    private val _filteredEvents = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _filteredEvents

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var dateRange: Pair<LocalDateTime?, LocalDateTime?> = Pair(null, null)

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            val events = repository.fetchEvents()
            _allEvents.value = events
            _filteredEvents.value = events
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun onDateRangeChange(start: LocalDateTime?, end: LocalDateTime?) {
        dateRange = start to end
        applyFilters()
    }

    private fun applyFilters() {
        val query = _searchQuery.value.lowercase()
        val (startDate, endDate) = dateRange

        _filteredEvents.value = _allEvents.value.filter { event ->
            val matchesText = event.title.lowercase().contains(query) ||
                    event.venue.title.lowercase().contains(query)

            val matchesDate = when {
                startDate != null && endDate != null ->
                    event.startDateTimeParsed in startDate..endDate
                startDate != null ->
                    event.startDateTimeParsed >= startDate
                endDate != null ->
                    event.startDateTimeParsed <= endDate
                else -> true
            }

            matchesText && matchesDate
        }
    }
}