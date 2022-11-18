package com.example.taskmanager.presentation.screens.reminderDetailsScreen

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.*
import androidx.navigation.compose.composable

private const val REMINDER_DETAILS_SCREEN_ROUTE = "reminder_details_screen"

fun NavGraphBuilder.reminderDetailsScreen(snackbarHostState: SnackbarHostState) {
    composable(
        route = "$REMINDER_DETAILS_SCREEN_ROUTE/{reminderId}",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://$REMINDER_DETAILS_SCREEN_ROUTE/{reminderId}"
            }
        ),
        arguments = listOf(
            navArgument("reminderId") {
                type = NavType.StringType
                defaultValue = "    "
            }
        )
    ) {
        val reminderId = it.arguments?.getString("reminderId") ?: "      "
        ReminderDetailsScreen(reminderId, snackbarHostState)
    }
}

fun NavHostController.navigateToReminderDetailsScreen(reminderId: String) {
    navigate("$REMINDER_DETAILS_SCREEN_ROUTE/$reminderId")
}