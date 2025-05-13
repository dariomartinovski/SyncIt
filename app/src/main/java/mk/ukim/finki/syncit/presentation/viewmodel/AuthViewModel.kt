package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mk.ukim.finki.syncit.data.repository.AuthRepository
import androidx.lifecycle.ViewModelProvider

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(authRepository.isUserLoggedIn())
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun checkAuthStatus() {
        _isLoggedIn.value = authRepository.isUserLoggedIn()
    }

    fun logout() {
        authRepository.logout()
        checkAuthStatus()
    }
}

class AuthViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authRepository) as T
    }
}