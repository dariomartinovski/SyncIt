package mk.ukim.finki.syncit.data.model

import mk.ukim.finki.syncit.data.model.enums.TicketStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Ticket(
    val id: String,
    val user: User,
    val event: Event,
    val uniqueCode: String,
    val ticketStatus: TicketStatus = TicketStatus.VALID
)