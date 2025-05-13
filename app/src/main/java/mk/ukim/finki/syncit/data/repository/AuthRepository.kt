package mk.ukim.finki.syncit.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mk.ukim.finki.syncit.data.model.User

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID is null")

            val userData = User(
                id = userId,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                email = email
            )

            firestore.collection("users").document(userId).set(userData).await()

            Result.success(Unit)
        } catch (e: Exception) {
            if (e.message?.contains("The email address is already in use") == true) {
                return Result.failure(Exception("Email already registered. Please log in."))
            }
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }
}