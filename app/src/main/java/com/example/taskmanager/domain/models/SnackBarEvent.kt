package com.example.taskmanager.domain.models

data class SnackBarEvent(
    val message: String,
    val actionTitle: String? = "Retry",
    val onActionClick: suspend () -> Unit = {}
)
