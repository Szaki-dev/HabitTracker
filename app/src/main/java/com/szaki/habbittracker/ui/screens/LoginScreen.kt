package com.szaki.habbittracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.szaki.habbittracker.data.AppPreferences
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val (savedEmail, savedRemember) = AppPreferences.loadLogin(context)

    var email by remember { mutableStateOf(savedEmail ?: "") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(savedRemember) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Automatikus beléptetés, ha rememberMe aktív
    LaunchedEffect(savedRemember, savedEmail) {
        if (savedRemember && !savedEmail.isNullOrBlank()) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bejelentkezés", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Jelszó") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Spacer(Modifier.width(4.dp))
                Text("Jegyezz meg")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    // Form validáció
                    if (!email.contains("@") || !email.contains(".")) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Érvénytelen email cím!")
                        }
                        return@Button
                    }
                    if (password.length < 6) {
                        scope.launch {
                            snackbarHostState.showSnackbar("A jelszónak legalább 6 karakteresnek kell lennie!")
                        }
                        return@Button
                    }

                    AppPreferences.saveLogin(context, email, rememberMe)

                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Belépés")
            }
        }
    }
}