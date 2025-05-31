package mk.ukim.finki.syncit.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mk.ukim.finki.syncit.data.model.User

class UserService(private val db: FirebaseFirestore) {

    suspend fun getUserById(userId: String): User? {
        return try {
            db.collection("users").document(userId).get().await().toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try {
            db.collection("users").document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
