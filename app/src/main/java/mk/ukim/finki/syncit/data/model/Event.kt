package mk.ukim.finki.syncit.data.model

import mk.ukim.finki.syncit.data.model.enums.Category
import java.time.LocalDateTime

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: Category = Category.OTHERS,
    val host: User = User(),
    val venue: Venue = Venue(),
    val entryFee: Long = 0,
    val startTime: String = "",
    val participants: List<String> = emptyList()
) {
    val startDateTimeParsed: LocalDateTime
        get() = LocalDateTime.parse(startTime)
}
