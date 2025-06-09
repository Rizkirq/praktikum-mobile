package com.example.BookApp.ui.screens

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _loginState.value = LoginState.Loading
                    }
                    is Result.Success -> {
                        _loginState.value = LoginState.Success(result.data)
                    }
                    is Result.Error -> {
                        _loginState.value = LoginState.Error(result.message)
                    }
                }
            }
        }
    }

    fun setError(message: String) {
        _loginState.value = LoginState.Error(message)
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}
