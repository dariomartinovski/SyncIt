package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    fun loadUser(userId: String) {
        viewModelScope.launch {
            repository.getUser(userId)?.let {
                _user.value = it
            }
        }
    }

    fun updateUser(updatedUser: User, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.updateUser(updatedUser)
            if (success) {
                _user.value = updatedUser
            }
            onComplete(success)
        }
    }
}
