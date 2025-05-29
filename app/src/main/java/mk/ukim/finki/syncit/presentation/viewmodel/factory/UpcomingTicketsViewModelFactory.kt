package mk.ukim.finki.syncit.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.syncit.data.repository.TicketRepository
import mk.ukim.finki.syncit.presentation.viewmodel.UpcomingTicketsViewModel

class UpcomingTicketsViewModelFactory(
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpcomingTicketsViewModel(ticketRepository) as T
    }
}