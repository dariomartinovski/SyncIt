package mk.ukim.finki.syncit.data.model

import mk.ukim.finki.syncit.data.model.enums.Category
import java.time.LocalDateTime

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val category: Category,
    val host: User,
    val venue: Venue,
    val entryFee: Long,
    val startTime: LocalDateTime,
    val participants: List<String> = emptyList()
)