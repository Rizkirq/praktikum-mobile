package com.example.BookApp.ui.screens.Splash

sealed class SplashState {
    object Loading : SplashState()
    object LoggedIn : SplashState()
    object NotLoggedIn : SplashState()
}