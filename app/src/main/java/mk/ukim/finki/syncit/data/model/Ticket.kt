package mk.ukim.finki.syncit.data.model

import mk.ukim.finki.syncit.data.model.enums.TicketStatus

data class Ticket(
    val id: String = "",
    val user: User = User(),
    val event: Event = Event(),
    val uniqueCode: String = "",
    val ticketStatus: TicketStatus = TicketStatus.VALID
)