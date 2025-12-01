package com.szaki.habbittracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.szaki.habbittracker.data.AppPreferences
import com.szaki.habbittracker.data.HabitData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    var habit by remember { mutableStateOf(AppPreferences.loadHabit(context)) }
    val email = AppPreferences.loadEmailOnly(context) ?: "Ismeretlen felhasználó"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HabitTracker") },
                actions = {
                    IconButton(onClick = { navController.navigate("add") }) {
                        Icon(Icons.Default.Edit, contentDescription = "Szerkesztés")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Beállítások")
                    }
                    if (email != "Ismeretlen felhasználó")
                        IconButton(onClick = {
                            AppPreferences.logout(context)
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Kilépés")
                        }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text("Bejelentkezve: $email")

            Spacer(Modifier.height(24.dp))

            if (habit == null) {
                Text("Nincs beállított szokás.")
                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    navController.navigate("add")
                }) {
                    Text("Szokás beállítása")
                }
            } else {
                val e: HabitData = habit!!

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Szokás: ${e.name}", style = MaterialTheme.typography.headlineLarge)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {
                        AppPreferences.deleteHabit(context)
                        habit = null
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Kiadás törlése")
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text("Cél: ${e.goal}")
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = e.done,
                        onCheckedChange = {
                            val updatedHabit = e.copy(done = it)
                            AppPreferences.saveHabit(context, updatedHabit)
                            habit = updatedHabit
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(if (habit?.done ?: false) "Ma teljesítve ✅" else "Ma még nem teljesítve")
                }
            }
        }
    }
}