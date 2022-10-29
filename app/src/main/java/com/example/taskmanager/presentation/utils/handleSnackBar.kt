package com.example.taskmanager.presentation.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.example.taskmanager.domain.models.SnackBarEvent

suspend fun SnackbarHostState.handleEvent(snackBarEvent: SnackBarEvent) {
    val result =
        showSnackbar(
            snackBarEvent.message,
            snackBarEvent.actionTitle,
            duration = SnackbarDuration.Short
        )

    if (result == SnackbarResult.ActionPerformed)
        snackBarEvent.onActionClick
}