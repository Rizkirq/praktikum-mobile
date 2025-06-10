package com.example.BookApp.ui.screens.LupaPassword

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
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState

    fun sendResetCode(email: String) {
        viewModelScope.launch {
            _forgotPasswordState.value = ForgotPasswordState.Loading
            if (email.isBlank()) {
                _forgotPasswordState.value =
                    ForgotPasswordState.Error("Email address cannot be empty.")
                return@launch
            }

            authRepository.forgotPasswordRequest(email).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _forgotPasswordState.value = ForgotPasswordState.Loading
                    is Result.Success -> _forgotPasswordState.value =
                        ForgotPasswordState.Success(result.data)
                    is Result.Error -> _forgotPasswordState.value =
                        ForgotPasswordState.Error(result.message)
                }
            }
        }
    }

    fun setError(message: String) {
        _forgotPasswordState.value = ForgotPasswordState.Error(message)
    }

    fun resetState() {
        _forgotPasswordState.value = ForgotPasswordState.Idle
    }
}