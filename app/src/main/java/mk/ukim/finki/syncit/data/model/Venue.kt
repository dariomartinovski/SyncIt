package mk.ukim.finki.syncit.data.model

data class Venue(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val maxCapacity: Long = 0,
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)