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

fun generateUniqueCode(): String {
    val now = LocalDateTime.now()
    val datePart = now.format(DateTimeFormatter.ofPattern("dd-MM"))
    val randomPart = (1..16)
        .map { ('A'..'Z') + ('0'..'9') }
        .flatten()
        .shuffled()
        .take(8)
        .joinToString("")

    return "$datePart-$randomPart"
}