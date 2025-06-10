package com.example.BookApp.ui.screens.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.helper.Result
import com.example.BookApp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _verifyEmailState = MutableStateFlow<VerifyEmailState>(VerifyEmailState.Idle)
    val verifyEmailState: StateFlow<VerifyEmailState> = _verifyEmailState

    val registeredEmail: StateFlow<String?> = savedStateHandle.getStateFlow("email", null)

    // --- State untuk Cooldown Resend Code ---
    private val COOLDOWN_MILLIS = 5 * 60 * 1000L // 5 menit dalam milidetik
    private val _resendCooldownEndTimeMillis = MutableStateFlow(0L) // Waktu kapan cooldown berakhir
    val resendCooldownEndTimeMillis: StateFlow<Long> = _resendCooldownEndTimeMillis.asStateFlow()


    fun verifyEmail(email: String, verificationCode: String) {
        viewModelScope.launch {
            _verifyEmailState.value = VerifyEmailState.Loading
            if (verificationCode.isBlank()) {
                _verifyEmailState.value =
                    VerifyEmailState.Error("Verification code cannot be empty.")
                return@launch
            }
            if (email.isBlank()) {
                _verifyEmailState.value =
                    VerifyEmailState.Error("Email is missing. Please restart the process or contact support.")
                return@launch
            }

            authRepository.verifyEmail(email, verificationCode).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _verifyEmailState.value = VerifyEmailState.Loading
                    is Result.Success -> _verifyEmailState.value = VerifyEmailState.Success(
                        result.data,
                        result.message ?: "Email verified successfully."
                    )
                    is Result.Error -> _verifyEmailState.value =
                        VerifyEmailState.Error(result.message)
                }
            }
        }
    }

    fun resendVerificationCode(email: String) {
        viewModelScope.launch {
            // Periksa cooldown sebelum mengirim
            val currentTime = System.currentTimeMillis()
            if (currentTime < _resendCooldownEndTimeMillis.value) {
                _verifyEmailState.value =
                    VerifyEmailState.Error("Please wait before resending the code.")
                return@launch
            }

            _verifyEmailState.value = VerifyEmailState.Loading
            if (email.isBlank()) {
                _verifyEmailState.value = VerifyEmailState.Error("Email is missing to resend code.")
                return@launch
            }

            authRepository.resendVerificationCode(email).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _verifyEmailState.value = VerifyEmailState.Loading
                    is Result.Success -> {
                        // Jika sukses, atur waktu cooldown baru
                        _resendCooldownEndTimeMillis.value = currentTime + COOLDOWN_MILLIS
                        _verifyEmailState.value = VerifyEmailState.ResendSuccess(result.data)
                    }
                    is Result.Error -> _verifyEmailState.value =
                        VerifyEmailState.Error(result.message)
                }
            }
        }
    }

    fun setError(message: String) {
        _verifyEmailState.value = VerifyEmailState.Error(message)
    }

    fun resetState() {
        _verifyEmailState.value = VerifyEmailState.Idle
    }
}