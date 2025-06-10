package com.example.BookApp.ui.screens.register

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun VerifyEmailScreen(
    viewModel: VerifyEmailViewModel = hiltViewModel(),
    registeredEmail: String?,
    onVerificationSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    // State untuk setiap digit OTP
    val otpDigits = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = List(6) { remember { FocusRequester() } }

    // Combine OTP digits into a single string
    val verificationCode = otpDigits.joinToString("")

    val verifyEmailState by viewModel.verifyEmailState.collectAsState()
    val isLoading = verifyEmailState is VerifyEmailState.Loading

    val resendCooldownEndTimeMillis by viewModel.resendCooldownEndTimeMillis.collectAsState()
    val timeRemainingState = remember { mutableLongStateOf(0L) }

    LaunchedEffect(resendCooldownEndTimeMillis) {
        // Setiap kali resendCooldownEndTimeMillis berubah (misal, setelah sukses kirim ulang)
        // atau saat composable pertama kali muncul (jika ada nilai yang tersimpan)
        while (true) {
            val currentTime = System.currentTimeMillis()
            val timeLeft = (resendCooldownEndTimeMillis - currentTime).coerceAtLeast(0L)
            timeRemainingState.longValue = timeLeft // Update state lokal
            if (timeLeft == 0L) {
                break // Hentikan loop jika cooldown sudah habis
            }
            delay(1000) // Update setiap 1 detik
        }
    }

    val isResendButtonEnabled = timeRemainingState.longValue == 0L && !isLoading
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemainingState.longValue)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemainingState.longValue) -
            TimeUnit.MINUTES.toSeconds(minutes)
    val formattedTime = String.format("%02d:%02d", minutes, seconds)



    // Observe Verify Email State
    LaunchedEffect(verifyEmailState) {
        when (verifyEmailState) {
            is VerifyEmailState.Loading -> {
                // Loading sudah dihandle di tombol
            }
            is VerifyEmailState.Success -> {
                val message = (verifyEmailState as VerifyEmailState.Success).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                onVerificationSuccess() // Navigasi ke Home atau Dashboard
            }
            is VerifyEmailState.ResendSuccess -> {
                val message = (verifyEmailState as VerifyEmailState.ResendSuccess).message
                snackbarHostState.showSnackbar(message)
                delay(500)
                viewModel.resetState()
            }
            is VerifyEmailState.Error -> {
                val message = (verifyEmailState as VerifyEmailState.Error).message
                snackbarHostState.showSnackbar(message)
                viewModel.resetState()
            }
            else -> {} // Idle
        }
    }

    LaunchedEffect(Unit) {
        if (!registeredEmail.isNullOrBlank()) {
            delay(100)
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
                text = "Enter confirmation code",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A 6-digit code was sent to \n${registeredEmail ?: "your email address"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // OTP Input Fields
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(6) { index ->
                    OutlinedTextField(
                        value = otpDigits[index],
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                otpDigits[index] = newValue
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
                            .weight(1f) // Membuat setiap field mengambil ruang yang sama
                            .padding(horizontal = 4.dp)
                            .focusRequester(focusRequesters[index]),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = VisualTransformation.None, // Agar angka terlihat jelas
                        textStyle = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF007BFF),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Resend
            TextButton(
                onClick = {
                    if (!registeredEmail.isNullOrBlank()) {
                        viewModel.resendVerificationCode(registeredEmail)
                    } else {
                        viewModel.setError("Email address is missing. Cannot resend code.")
                    }
                },
                enabled = isResendButtonEnabled,
                contentPadding = PaddingValues(0.dp)
            ) {
                if (timeRemainingState.longValue > 0) { // Gunakan timeRemainingState
                    Text(
                        "Resend code in $formattedTime",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        "Resend code",
                        color = Color(0xFF007BFF),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Button
            Button(
                onClick = {
                    if (registeredEmail.isNullOrBlank()) {
                        viewModel.setError("Email address is missing. Cannot verify.")
                        return@Button
                    }
                    if (verificationCode.length != 6) {
                        viewModel.setError("Please enter the full 6-digit code.")
                        return@Button
                    }
                    viewModel.verifyEmail(registeredEmail, verificationCode)
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
                        text = if (isLoading) "Verifying..." else "Continue",
                        color = Color.White
                    )
                }
            }
        }
    }
}
