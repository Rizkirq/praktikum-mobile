package com.example.BookApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.BookApp.ui.screens.LoginScreen
import com.example.BookApp.ui.screens.RegisterScreen

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {
        composable(Destinations.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    // TODO: Nanti navigasi ke Home
                },
                onRegisterClick = {
                    navController.navigate(Destinations.REGISTER)
                },
                onForgotPasswordClick = {
                    // TODO: Forgot Password Screen kalau ada
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterClick = {
                    // TODO: Register success navigate ke Home
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
