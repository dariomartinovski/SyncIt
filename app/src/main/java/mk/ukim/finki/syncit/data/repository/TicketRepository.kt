package mk.ukim.finki.syncit.data.repository

import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.remote.TicketService

class TicketRepository(private val ticketService: TicketService) {

    suspend fun reserveTickets(user: User, event: Event, quantity: Int): Result<Unit> {
        return ticketService.reserveTickets(user, event, quantity)
    }
}