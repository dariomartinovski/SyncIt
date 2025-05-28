package mk.ukim.finki.syncit.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.remote.EventService
import mk.ukim.finki.syncit.data.remote.TicketService
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.data.repository.TicketRepository
import mk.ukim.finki.syncit.presentation.viewmodel.BuyTicketsViewModel

class BuyTicketsViewModelFactory(private val eventId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuyTicketsViewModel::class.java)) {
            val service = TicketService(FirebaseFirestore.getInstance())
            val repository = TicketRepository(service)
            val eventService = EventService(FirebaseFirestore.getInstance())
            val eventRepository = EventRepository(eventService)
            @Suppress("UNCHECKED_CAST")
            return BuyTicketsViewModel(eventId, repository, eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}