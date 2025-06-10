package com.example.BookApp.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun validateAndProceed(email: String, code: String) {
        viewModelScope.launch {
            _enterResetCodeState.value = EnterResetCodeState.Loading
            if (code.isBlank()) {
                _enterResetCodeState.value = EnterResetCodeState.Error("Reset code cannot be empty.")
                return@launch
            }
            if (code.length != 6 || !code.all { it.isDigit() }) {
                _enterResetCodeState.value = EnterResetCodeState.Error("Invalid reset code format. It should be 6 digits.")
                return@launch
            }
            if (email.isBlank()) { // Tambahkan validasi email
                _enterResetCodeState.value = EnterResetCodeState.Error("Email is missing. Please restart the process.")
                return@launch
            }

            authRepository.checkResetToken(email, code).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _enterResetCodeState.value = EnterResetCodeState.Loading
                    is Result.Success -> _enterResetCodeState.value = EnterResetCodeState.Success(result.data) // result.data adalah pesan sukses String
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