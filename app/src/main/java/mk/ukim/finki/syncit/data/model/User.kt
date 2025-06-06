package mk.ukim.finki.syncit.data.model

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = ""
) {
    val fullName: String
        get() = "$firstName $lastName"
}