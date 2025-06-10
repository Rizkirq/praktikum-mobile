package com.example.BookApp.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Karena ini hanya validasi lokal, AuthRepository tidak diinject langsung di sini
// Ini menjaga ViewModel tetap ringan jika tidak ada panggilan API
// Jika nanti Anda mau menambahkan API untuk verifikasi kode, baru AuthRepository diinject
class EnterResetCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _enterResetCodeState = MutableStateFlow<EnterResetCodeState>(EnterResetCodeState.Idle)
    val enterResetCodeState: StateFlow<EnterResetCodeState> = _enterResetCodeState

    // Email yang diteruskan dari ForgotPasswordScreen
    val userEmail: StateFlow<String?> = savedStateHandle.getStateFlow("email", null)

    fun validateAndProceed(code: String) {
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

            // Di sini, tidak ada panggilan API, hanya validasi lokal.
            // Jika valid, kita anggap sukses dan siap untuk navigasi.
            _enterResetCodeState.value = EnterResetCodeState.Success("Code validated. Proceeding to password reset.")
        }
    }

    fun setError(message: String) {
        _enterResetCodeState.value = EnterResetCodeState.Error(message)
    }

    fun resetState() {
        _enterResetCodeState.value = EnterResetCodeState.Idle
    }
}