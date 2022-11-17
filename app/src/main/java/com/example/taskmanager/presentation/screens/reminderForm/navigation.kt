package com.example.taskmanager.presentation.screens.reminderForm

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.*
import androidx.navigation.compose.composable

private const val reminderRoute = "reminders"

fun NavGraphBuilder.reminderFormScreen(
    snackbarHostState: SnackbarHostState,
    onReminderSaved: (String) -> Unit = {},
) {
    composable("$reminderRoute/{reminderId}") { backStackEntry ->
        val reminderId = backStackEntry.arguments?.getString("reminderId") ?: "     "
        ReminderFormScreen(
            reminderId = reminderId,
            snackbarHostState = snackbarHostState,
            onReminderSaved = onReminderSaved
        )
    }
}

fun NavHostController.navigateToReminderFormScreen() {
    navigate("$reminderRoute/   ") {
        launchSingleTop = true
    }
}

fun NavHostController.navigateToReminderFormScreen(reminderId: String) {
    navigate("$reminderRoute/$reminderId") {
        launchSingleTop = true
    }
}