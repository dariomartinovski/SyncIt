package mk.ukim.finki.syncit.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.syncit.data.repository.TicketRepository
import mk.ukim.finki.syncit.presentation.viewmodel.TicketDetailsViewModel

class TicketDetailsViewModelFactory(
    private val repository: TicketRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketDetailsViewModel::class.java)) {
            return TicketDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}