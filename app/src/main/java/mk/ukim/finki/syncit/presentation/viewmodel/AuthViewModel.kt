package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.repository.AuthRepository

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(authRepository.isUserLoggedIn())
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        if (_isLoggedIn.value) {
            loadCurrentUser()
        }
    }

    fun checkAuthStatus() {
        _isLoggedIn.value = authRepository.isUserLoggedIn()
        if (_isLoggedIn.value) {
            loadCurrentUser()
        } else {
            _currentUser.value = null
        }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            val result = authRepository.getCurrentUserProfile()
            val user = result.getOrNull()
            println("Fetched current user: $user")
            _currentUser.value = user
        }
    }

    fun logout() {
        authRepository.logout()
        checkAuthStatus()
    }
}