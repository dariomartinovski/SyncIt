package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.data.repository.TicketRepository
import androidx.compose.runtime.State

class BuyTicketsViewModel(
    private val eventId: String,
    private val ticketRepository: TicketRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _event = mutableStateOf<Event?>(null)
    val event: State<Event?> = _event

    var quantity: Int = 1
        set(value) {
            field = if (value < 1) 1 else value
        }

    init {
        fetchEvent()
    }

    private fun fetchEvent() {
        viewModelScope.launch {
            _event.value = eventRepository.fetchEventById(eventId)
        }
    }

    fun reserveTickets(user: User, onResult: (Boolean) -> Unit) {
        val currentEvent = _event.value ?: return onResult(false)
        viewModelScope.launch {
            val result = ticketRepository.reserveTickets(user, currentEvent, quantity)
            onResult(result.isSuccess)
        }
    }
}