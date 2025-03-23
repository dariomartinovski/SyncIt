package mk.ukim.finki.syncit.data.model

data class Venue(
    val id: String,
    val title: String,
    val description: String,
    val maxCapacity: Long,
    val latitude: Double,
    val longitude: Double,
    val eventIds: List<String> = emptyList()
)