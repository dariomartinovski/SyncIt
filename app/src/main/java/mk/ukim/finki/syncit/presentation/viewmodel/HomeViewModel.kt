package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.repository.EventRepository
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class HomeViewModel(private val repository: EventRepository) : ViewModel() {

    private val _events = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _events

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _events.value = repository.fetchEvents()
        }
    }
}