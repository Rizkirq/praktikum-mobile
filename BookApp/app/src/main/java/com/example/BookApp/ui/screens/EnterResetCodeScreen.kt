package com.example.BookApp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay


@Composable
fun EnterResetCodeScreen(
    viewModel: EnterResetCodeViewModel = hiltViewModel(),
    userEmail: String?, // Email dari NavArguments
    onCodeValidatedAndProceed: (String, String) -> Unit, // Callback untuk navigasi ke ResetPasswordScreen (email, token)
    onBackToLogin: () -> Unit // Callback untuk kembali ke LoginScreen
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    // State untuk setiap digit kode reset
    val codeDigits = remember { mutableStateListOf("", "", "", "", "", "") } // 6 digit
    val focusRequesters = List(6) { remember { FocusRequester() } } // Fokus untuk setiap digit

    // Gabungkan digit-digit menjadi satu string
    val resetCode = codeDigits.joinToString("")

    val enterResetCodeState by viewModel.enterResetCodeState.collectAsState()
    val isLoading = enterResetCodeState is EnterResetCodeState.Loading

    // Observe Enter Reset Code State
    LaunchedEffect(enterResetCodeState) {
        when (enterResetCodeState) {
            is EnterResetCodeState.Loading -> {
                // Loading dihandle di tombol
            }
            is EnterResetCodeState.Success -> {
                val message = (enterResetCodeState as EnterResetCodeState.Success).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                // Navigasi ke ResetPasswordScreen dengan email dan kode
                onCodeValidatedAndProceed(userEmail ?: "", resetCode)
            }
            is EnterResetCodeState.Error -> {
                val message = (enterResetCodeState as EnterResetCodeState.Error).message
                snackbarHostState.showSnackbar(message)
                viewModel.resetState()
            }
            else -> {} // Idle
        }
    }

    // Awalnya, fokus ke digit pertama jika email ada
    LaunchedEffect(Unit) {
        if (!userEmail.isNullOrBlank()) {
            delay(100) // Beri waktu UI untuk di-compose
            focusRequesters[0].requestFocus()
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F9FF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Enter Reset Code",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A 6-digit code was sent to \n${userEmail ?: "your email address"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // OTP Input Fields (for reset code)
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(6) { index ->
                    OutlinedTextField(
                        value = codeDigits[index],
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                codeDigits[index] = newValue
                                if (newValue.isNotEmpty()) {
                                    // Pindah fokus ke digit berikutnya
                                    if (index < 5) {
                                        focusRequesters[index + 1].requestFocus()
                                    } else {
                                        // Jika digit terakhir, sembunyikan keyboard
                                        focusManager.clearFocus()
                                    }
                                } else {
                                    // Jika dihapus dan kosong, pindah fokus ke digit sebelumnya
                                    if (index > 0) {
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .focusRequester(focusRequesters[index]),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = VisualTransformation.None,
                        textStyle = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF007BFF),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Button (Verify Code)
            Button(
                onClick = {
                    if (userEmail.isNullOrBlank()) {
                        viewModel.setError("Email address is missing. Cannot proceed.")
                        return@Button
                    }
                    if (resetCode.length != 6) {
                        viewModel.setError("Please enter the full 6-digit code.")
                        return@Button
                    }
                    viewModel.validateAndProceed(resetCode) // Memanggil validasi lokal
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp),
                            strokeWidth = 2.dp
                        )
                    }
                    Text(
                        text = if (isLoading) "Verifying Code..." else "Continue",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onBackToLogin) {
                Text(
                    "Cancel and go back to Login",
                    color = Color(0xFF007BFF),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}