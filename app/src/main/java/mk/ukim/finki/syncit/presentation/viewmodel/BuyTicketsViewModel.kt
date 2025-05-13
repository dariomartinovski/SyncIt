package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.Event

class BuyTicketsViewModel(eventId: String) : ViewModel() {

    val event: Event? = MockData.events.find { it.id == eventId }

    var quantity: Int = 1
        set(value) {
            field = if (value < 1) 1 else value
        }

    val totalAmount: Long
        get() = (event?.entryFee ?: 0) * quantity

    fun reserveTickets() {
        println("Reserved $quantity tickets for ${event?.title}")
    }
}
