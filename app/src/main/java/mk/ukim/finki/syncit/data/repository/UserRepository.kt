package mk.ukim.finki.syncit.data.repository

import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.remote.UserService

class UserRepository(private val service: UserService) {
    suspend fun getUser(userId: String): User? = service.getUserById(userId)
    suspend fun updateUser(user: User): Boolean = service.updateUser(user)
}
