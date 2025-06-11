package com.example.BookApp.ui.screens.Splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashState: StateFlow<SplashState> = _splashState

    init {
        viewModelScope.launch {
            val user = authRepository.getLoggedInUser().firstOrNull()
            val finalDeterminedState = if (user != null && user.token.isNotEmpty()) {
                SplashState.LoggedIn
            } else {
                SplashState.NotLoggedIn
            }

            delay(3000)

            _splashState.value = finalDeterminedState
        }
    }
}