package com.example.BookApp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterClick: (String) -> Unit, // Tanda tangan diubah untuk menerima email
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) } // Untuk Terms & Conditions
    val registerState by viewModel.registerState.collectAsState()
    val isLoading = registerState is RegisterState.Loading
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Loading -> {
                // Loading sudah dihandle di button
            }
            is RegisterState.Success -> {
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                val registeredEmail = (registerState as RegisterState.Success).user.email // Ambil email dari user object
                onRegisterClick(registeredEmail) // Kirim email ke NavGraph untuk navigasi
            }
            is RegisterState.Error -> {
                val message = (registerState as RegisterState.Error).message
                snackbarHostState.showSnackbar(message)
                viewModel.resetState()
            }
            else -> {} // Idle
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

            // Logo Placeholder (jika R.drawable.ic_placeholder ada)
            // Icon(
            //     painter = painterResource(id = R.drawable.ic_placeholder),
            //     contentDescription = "Logo",
            //     tint = Color(0xFFB0C4DE),
            //     modifier = Modifier.size(64.dp)
            // )
            // Spacer(modifier = Modifier.height(40.dp)) // Hanya jika ada logo

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create an account to get started",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.85f),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.85f),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "I have read and agree to the Terms and Conditions and the Privacy Policy.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Register Button
            Button(
                onClick = {
                    if (!isChecked) {
                        viewModel.setError("You must agree to the Terms and Conditions and Privacy Policy.")
                    } else {
                        // Lakukan validasi input sebelum memanggil ViewModel
                        if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank()) {
                            viewModel.setError("All fields must be filled.")
                        } else {
                            viewModel.register(
                                name.value,
                                email.value,
                                password.value,
                                confirmPassword.value
                            )
                        }
                    }
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
                        text = if (isLoading) "Registering..." else "Register",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Have an Account ?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                TextButton(
                    onClick = onLoginClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Login",
                        color = Color(0xFF007BFF),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}