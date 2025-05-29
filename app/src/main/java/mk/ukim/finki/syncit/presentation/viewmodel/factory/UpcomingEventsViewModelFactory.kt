package mk.ukim.finki.syncit.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.presentation.viewmodel.UpcomingEventsViewModel

class UpcomingEventsViewModelFactory(
    private val repository: EventRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpcomingEventsViewModel(repository) as T
    }
}