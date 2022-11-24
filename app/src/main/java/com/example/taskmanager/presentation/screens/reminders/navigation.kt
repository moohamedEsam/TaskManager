package com.example.taskmanager.presentation.screens.reminders

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun Screens.remindersScreenRoute() = "reminders_screen"

fun NavGraphBuilder.remindersScreen(onReminderClick: (String) -> Unit) {
    composable("reminders_screen") {
        RemindersScreen(onReminderClick)
    }
}

fun NavHostController.navigateToRemindersScreen() {
    navigate("reminders_screen"){
        launchSingleTop = true
    }
}