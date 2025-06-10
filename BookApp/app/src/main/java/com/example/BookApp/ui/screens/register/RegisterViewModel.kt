package com.example.BookApp.ui.screens.register

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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            if (password != confirmPassword) {
                _registerState.value =
                    RegisterState.Error("Password and Confirm Password do not match")
                return@launch
            }
            if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                _registerState.value = RegisterState.Error("All fields must be filled")
                return@launch
            }


            authRepository.register(name, email, password, confirmPassword).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _registerState.value = RegisterState.Loading
                    }
                    is Result.Success -> {
                        _registerState.value = RegisterState.Success(result.data)
                    }
                    is Result.Error -> {
                        _registerState.value = RegisterState.Error(result.message)
                    }
                }
            }
        }
    }

    fun setError(message: String) {
        _registerState.value = RegisterState.Error(message)
    }

    fun resetState() {
        _registerState.value = RegisterState.Idle
    }
}
