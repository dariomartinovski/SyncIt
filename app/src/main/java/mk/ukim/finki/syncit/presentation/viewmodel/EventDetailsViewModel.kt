package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.Event

class EventDetailsViewModel(eventId: String) : ViewModel() {

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    init {
        _event.value = MockData.events.find { it.id == eventId }
    }
}