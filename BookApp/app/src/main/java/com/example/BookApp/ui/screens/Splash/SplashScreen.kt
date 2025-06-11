package com.example.BookApp.ui.screens.Splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.BookApp.R
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onNotLoggedIn: () -> Unit
) {
    val splashState by viewModel.splashState.collectAsState()

    LaunchedEffect(splashState) {
        when (splashState) {
            is SplashState.Loading -> {

            }
            is SplashState.LoggedIn -> {
                onLoggedIn()
            }
            is SplashState.NotLoggedIn -> {
                onNotLoggedIn()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bookapp_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(214.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF2E3A4D))) {
                    append("Book")
                }
                withStyle(style = SpanStyle(color = Color(0xFF6A8099))) {
                    append("App")
                }
            },
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
        )


        Spacer(modifier = Modifier.height(32.dp))
    }
}
