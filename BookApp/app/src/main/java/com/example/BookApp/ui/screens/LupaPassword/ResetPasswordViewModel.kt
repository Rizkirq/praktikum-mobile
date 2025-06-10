package com.example.BookApp.ui.screens.LupaPassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.helper.Result
import com.example.BookApp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle // Untuk mendapatkan argumen email dari navigasi
) : ViewModel() {

    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Idle)
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState

    // Email yang diteruskan dari ForgotPasswordScreen
    val userEmail: StateFlow<String?> = savedStateHandle.getStateFlow("email", null)

    fun resetPassword(email: String, token: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _resetPasswordState.value = ResetPasswordState.Loading
            if (email.isBlank() || token.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                _resetPasswordState.value = ResetPasswordState.Error("All fields must be filled.")
                return@launch
            }
            if (password != confirmPassword) {
                _resetPasswordState.value =
                    ResetPasswordState.Error("Password and Confirm Password do not match.")
                return@launch
            }
            if (password.length < 8) {
                _resetPasswordState.value =
                    ResetPasswordState.Error("Password must be at least 8 characters long.")
                return@launch
            }

            authRepository.resetPassword(email, token, password, confirmPassword).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _resetPasswordState.value = ResetPasswordState.Loading
                    is Result.Success -> _resetPasswordState.value =
                        ResetPasswordState.Success(result.data)
                    is Result.Error -> _resetPasswordState.value =
                        ResetPasswordState.Error(result.message)
                }
            }
        }
    }

    fun setError(message: String) {
        _resetPasswordState.value = ResetPasswordState.Error(message)
    }

    fun resetState() {
        _resetPasswordState.value = ResetPasswordState.Idle
    }
}