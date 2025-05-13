package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.repository.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String,
        password: String
    ) {
        _registerState.value = RegisterState.Loading

        viewModelScope.launch {
            val result = authRepository.registerUser(
                firstName, lastName, phoneNumber, email, password
            )
            _registerState.value = if (result.isSuccess) {
                RegisterState.Success
            } else {
                RegisterState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}