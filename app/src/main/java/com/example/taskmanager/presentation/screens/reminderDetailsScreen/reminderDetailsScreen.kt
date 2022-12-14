package com.example.taskmanager.presentation.screens.reminderDetailsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.presentation.composables.ActionBar
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat

@Composable
fun ReminderDetailsScreen(
    reminderId: String,
    snackbarHostState: SnackbarHostState,
    viewModel: ReminderDetailsViewModel = koinViewModel(parameters = { parametersOf(reminderId) })
) {
    val reminderState by viewModel.reminder.collectAsState()
    LaunchedEffect(key1 = reminderState) {
        if (reminderState is Resource.Error)
            snackbarHostState.showSnackbar(message = reminderState.message ?: "Reminder not found")
    }
    ReminderDetailsScreen(reminder = reminderState.data ?: return)
}

@Composable
fun ReminderDetailsScreen(
    reminder: Reminder
) {
    val simpleFormatter by remember {
        mutableStateOf(SimpleDateFormat("dd MMM hh:mm a"))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionBar(
            title = reminder.title,
            isPinned = reminder.isPinned,
            isFavorite = reminder.isFavorite,
            isDeleted = reminder.isDeleted,
            onEditClick = { }
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reminder.tags) {
                TextButton(onClick = { }) {
                    Text(text = it.name)
                }
            }
        }
        Text(text = reminder.description, style = MaterialTheme.typography.bodyLarge)
        Text(text = "date: ${simpleFormatter.format(reminder.date)}")
    }
}