package mk.ukim.finki.syncit.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.presentation.viewmodel.EventDetailsViewModel

class EventDetailsViewModelFactory(
    private val eventId: String,
    private val repository: EventRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventDetailsViewModel(eventId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}