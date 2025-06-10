package com.example.BookApp.ui.screens.LupaPassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.BookApp.helper.Result
import com.example.BookApp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class EnterResetCodeViewModel @Inject constructor(
    private val authRepository: AuthRepository, // <-- Inject AuthRepository di sini
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _enterResetCodeState = MutableStateFlow<EnterResetCodeState>(EnterResetCodeState.Idle)
    val enterResetCodeState: StateFlow<EnterResetCodeState> = _enterResetCodeState

    val userEmail: StateFlow<String?> = savedStateHandle.getStateFlow("email", null)

    private val COOLDOWN_MILLIS = 5 * 60 * 1000L
    private val _resendCooldownEndTimeMillis = MutableStateFlow(0L)
    val resendCooldownEndTimeMillis: StateFlow<Long> = _resendCooldownEndTimeMillis.asStateFlow()

    fun validateAndProceed(email: String, code: String) {
        viewModelScope.launch {
            _enterResetCodeState.value = EnterResetCodeState.Loading
            if (code.isBlank()) {
                _enterResetCodeState.value =
                    EnterResetCodeState.Error("Reset code cannot be empty.")
                return@launch
            }
            if (code.length != 6 || !code.all { it.isDigit() }) {
                _enterResetCodeState.value =
                    EnterResetCodeState.Error("Invalid reset code format. It should be 6 digits.")
                return@launch
            }
            if (email.isBlank()) { // Tambahkan validasi email
                _enterResetCodeState.value =
                    EnterResetCodeState.Error("Email is missing. Please restart the process.")
                return@launch
            }

            authRepository.checkResetToken(email, code).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _enterResetCodeState.value = EnterResetCodeState.Loading
                    is Result.Success -> _enterResetCodeState.value =
                        EnterResetCodeState.Success(result.data) // result.data adalah pesan sukses String
                    is Result.Error -> _enterResetCodeState.value =
                        EnterResetCodeState.Error(result.message)
                }
            }
        }
    }

    fun resendResetCode(email: String) {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()
            if (currentTime < _resendCooldownEndTimeMillis.value) {
                _enterResetCodeState.value = EnterResetCodeState.Error("Please wait before resending the code.")
                return@launch
            }

            _enterResetCodeState.value = EnterResetCodeState.Loading
            if (email.isBlank()) {
                _enterResetCodeState.value = EnterResetCodeState.Error("Email is missing to resend code.")
                return@launch
            }

            authRepository.forgotPasswordRequest(email).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _enterResetCodeState.value = EnterResetCodeState.Loading
                    is Result.Success -> {
                        _resendCooldownEndTimeMillis.value = currentTime + COOLDOWN_MILLIS
                        _enterResetCodeState.value = EnterResetCodeState.ResendSuccess("New reset code sent to your email.")
                    }
                    is Result.Error -> _enterResetCodeState.value = EnterResetCodeState.Error(result.message)
                }
            }
        }
    }

    fun setError(message: String) {
        _enterResetCodeState.value = EnterResetCodeState.Error(message)
    }

    fun resetState() {
        _enterResetCodeState.value = EnterResetCodeState.Idle
    }
}