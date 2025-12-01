package com.szaki.habbittracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.szaki.habbittracker.data.AppPreferences
import com.szaki.habbittracker.ui.screens.AddHabbitScreen
import com.szaki.habbittracker.ui.screens.HomeScreen
import com.szaki.habbittracker.ui.screens.LoginScreen
import com.szaki.habbittracker.ui.screens.SettingsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val (savedEmail, savedRemember) = AppPreferences.loadLogin(context)
    val isLoggedIn = savedRemember && !savedEmail.isNullOrBlank()
    val startDestination = if (isLoggedIn) "home" else "login"
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("add") {
            AddHabbitScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
    }
}