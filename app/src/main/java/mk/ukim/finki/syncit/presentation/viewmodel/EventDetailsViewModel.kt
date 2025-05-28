package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.repository.EventRepository

class EventDetailsViewModel(
    private val eventId: String,
    private val repository: EventRepository
) : ViewModel() {

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    init {
        viewModelScope.launch {
            _event.value = repository.fetchEventById(eventId)
        }
    }
}