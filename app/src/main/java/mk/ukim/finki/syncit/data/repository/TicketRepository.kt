package mk.ukim.finki.syncit.data.repository

import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.Ticket
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.remote.TicketService

class TicketRepository(private val ticketService: TicketService) {

    suspend fun reserveTickets(user: User, event: Event, quantity: Int): Result<Unit> {
        return ticketService.reserveTickets(user, event, quantity)
    }

    suspend fun getAllTickets(): List<Ticket> {
        return ticketService.getTickets()
    }

    suspend fun getTicketsForUser(userId: String): List<Ticket> {
        return ticketService.getTicketsForUser(userId)
    }

    suspend fun getTicketById(ticketId: String): Ticket? {
        return ticketService.getTicketById(ticketId)
    }

}