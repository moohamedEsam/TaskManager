package com.example.taskmanager.domain.dataModels

data class SnackBarEvent(
    val message: String,
    val actionTitle: String? = "Retry",
    val onActionClick: () -> Unit = {}
)
