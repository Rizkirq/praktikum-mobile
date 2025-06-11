package com.example.BookApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NamedNavArgument
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import com.example.BookApp.ui.navigation.Destinations.EXPLORE
import com.example.BookApp.ui.screens.BookDetail.BookDetailScreen
import com.example.BookApp.ui.screens.Explore.ExploreScreen
import com.example.BookApp.ui.screens.LupaPassword.ForgotPasswordScreen
import com.example.BookApp.ui.screens.login.LoginScreen
import com.example.BookApp.ui.screens.register.RegisterScreen
import com.example.BookApp.ui.screens.LupaPassword.ResetPasswordScreen
import com.example.BookApp.ui.screens.register.VerifyEmailScreen
import com.example.BookApp.ui.screens.LupaPassword.EnterResetCodeScreen
import com.example.BookApp.ui.screens.home.HomeScreen

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val VERIFY_EMAIL = "verify_email/{email}"
    const val FORGOT_PASSWORD = "forgot_password"
    const val ENTER_RESET_CODE = "enter_reset_code/{email}"
    const val RESET_PASSWORD = "reset_password/{email}/{token}"
    const val HOME = "home"
    const val BOOK_DETAIL = "book_detail/{bookId}"
    const val EXPLORE = "explore"

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
                    navController.navigate(Destinations.FORGOT_PASSWORD)
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterClick = { registeredEmail ->
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
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onResetCodeSent = { email ->
                    navController.navigate("enter_reset_code/$email") { // <-- Baris yang benar
                        popUpTo(Destinations.FORGOT_PASSWORD) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Destinations.ENTER_RESET_CODE,
            arguments = listOf(navArgument("email") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            EnterResetCodeScreen(
                userEmail = email,
                onCodeValidatedAndProceed = { validatedEmail, resetToken ->
                    // Navigasi ke ResetPasswordScreen setelah kode divalidasi, bawa email dan token
                    navController.navigate("reset_password/$validatedEmail/$resetToken") {
                        popUpTo(Destinations.ENTER_RESET_CODE) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Destinations.RESET_PASSWORD,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType; nullable = true },
                navArgument("token") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val token = backStackEntry.arguments?.getString("token")
            ResetPasswordScreen(
                resetEmail = email,
                resetToken = token,
                onPasswordResetSuccess = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.HOME) {
            HomeScreen(
                onNavigateToExplore = { navController.navigate(Destinations.EXPLORE) },
                onNavigateToLibrary = { navController.navigate("library_route_placeholder") },
                onNavigateToProfile = { navController.navigate("profile_route_placeholder") },
                onNavigateToSearch = { navController.navigate("search_route_placeholder") },
                onBookClick = { bookId ->
                    navController.navigate("book_detail/$bookId")
                }
            )
        }

        composable(
            route = Destinations.BOOK_DETAIL,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            BookDetailScreen(
                bookId = bookId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(EXPLORE) {
            ExploreScreen(onBookClick = { bookId ->
                navController.navigate("book_detail/$bookId")
            })
        }

    }
}
