package com.example.BookApp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import com.example.BookApp.ui.screens.LoginScreen
import com.example.BookApp.ui.screens.RegisterScreen
import com.example.BookApp.ui.screens.VerifyEmailScreen

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val VERIFY_EMAIL = "verify_email/{email}"
    const val HOME = "home"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {
        composable(Destinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Destinations.REGISTER)
                },
                onForgotPasswordClick = {
                    // Forgot password screen
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterClick = { registeredEmail ->
                    // GANTI INI: navController.navigate("${Destinations.VERIFY_EMAIL}/$registeredEmail")
                    // DENGAN INI:
                    navController.navigate("verify_email/$registeredEmail") { // <--- Baris yang benar
                        popUpTo(Destinations.REGISTER) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Destinations.VERIFY_EMAIL,
            arguments = listOf(navArgument("email") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            VerifyEmailScreen(
                registeredEmail = email,
                onVerificationSuccess = {
                    // Navigate to Home after successful email verification
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true } // Hapus semua layar auth dari back stack
                    }
                },
                onBackToLogin = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.LOGIN) { inclusive = true } // Kembali ke login, hapus yang lain
                    }
                }
            )
        }

    }
}
