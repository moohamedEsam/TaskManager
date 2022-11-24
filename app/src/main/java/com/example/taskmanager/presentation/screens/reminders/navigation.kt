package com.example.taskmanager.presentation.screens.reminders

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun Screens.remindersScreenRoute() = "Reminders"

fun NavGraphBuilder.remindersScreen(onReminderClick: (String) -> Unit) {
    composable(Screens.remindersScreenRoute()) {
        RemindersScreen(onReminderClick)
    }
}

fun NavHostController.navigateToRemindersScreen() {
    navigate(Screens.remindersScreenRoute()){
        launchSingleTop = true
    }
}