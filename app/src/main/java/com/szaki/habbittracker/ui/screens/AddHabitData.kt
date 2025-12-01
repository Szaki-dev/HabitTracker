package com.szaki.habbittracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.szaki.habbittracker.data.AppPreferences
import com.szaki.habbittracker.data.HabitData
import kotlinx.coroutines.launch

@Composable
fun AddHabbitScreen(navController: NavController) {
    val context = LocalContext.current
    var habit by remember { mutableStateOf(AppPreferences.loadHabit(context)) }

    var habitName by remember { mutableStateOf(habit?.name ?: "") }
    var habitGoal by remember { mutableStateOf(habit?.goal ?: "") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text("Szokás szerkesztése", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text("Szokás neve") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = habitGoal,
                onValueChange = { habitGoal = it },
                label = { Text("Cél (pl. 10 000 lépés)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (habitName.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("A név megadása kötelező!")
                        }
                        return@Button
                    }
                    if (habitGoal.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("A cél megadása kötelező!")
                        }
                        return@Button
                    }

                    val habit = HabitData(
                        name = habitName,
                        goal = habitGoal
                    )

                    AppPreferences.saveHabit(context, habit)

                    scope.launch {
                        snackbarHostState.showSnackbar("Szokás mentve")
                    }

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mentés")
            }
        }
    }
}