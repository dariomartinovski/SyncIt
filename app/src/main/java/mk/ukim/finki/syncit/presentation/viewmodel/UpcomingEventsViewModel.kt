package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.repository.EventRepository
import java.time.LocalDateTime

class UpcomingEventsViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _createdTab = MutableStateFlow(0)
    val createdTab: StateFlow<Int> = _createdTab

    private val _createdEvents = MutableStateFlow<List<Event>>(emptyList())
    val createdEvents: StateFlow<List<Event>> = _createdEvents

    val filteredEvents: StateFlow<List<Event>> = combine(_createdTab, _createdEvents) { tab, events ->
        val now = LocalDateTime.now()
        if (tab == 0) {
            events.filter { it.startDateTimeParsed.isAfter(now) }
        } else {
            events.filter { it.startDateTimeParsed.isBefore(now) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setCreatedTab(index: Int) {
        _createdTab.value = index
    }

    fun fetchEvents(userId: String) {
        viewModelScope.launch {
            val events = repository.getEventsForUser(userId)
            _createdEvents.value = events
        }
    }
}