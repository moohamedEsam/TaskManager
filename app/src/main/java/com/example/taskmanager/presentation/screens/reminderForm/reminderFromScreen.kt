package com.example.taskmanager.presentation.screens.reminderForm

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.presentation.composables.TagDialog
import com.example.taskmanager.presentation.composables.TagRow
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors
import com.example.taskmanager.presentation.utils.handleEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun ReminderFormScreen(
    reminderId: String,
    snackbarHostState: SnackbarHostState,
    onReminderSaved: (String) -> Unit = {},
    viewModel: ReminderFormViewModel = koinViewModel(parameters = { parametersOf(reminderId) })
) {
    val reminderState by viewModel.reminder.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.receiveChannel.collectLatest {
            snackbarHostState.handleEvent(it)
        }
    }
    ReminderFormScreen(
        reminder = reminderState.data ?: return,
        showSaveButton = viewModel.shouldShowSaveButton,
        onTitleChanged = viewModel::updateTitle,
        onDescriptionChanged = viewModel::updateDescription,
        onToggleTagDialog = viewModel::toggleTagDialog,
        onSetLocalDate = { viewModel.localDate = it },
        onSetLocalTime = { viewModel.localTime = it },
        onSetDate = viewModel::updateDate,
        onSaveClick = { viewModel.saveReminder(onReminderSaved) }
    )

    TagDialog(
        showDialog = viewModel.showTagDialog,
        tagsState = viewModel.tags,
        onTagSelected = viewModel::addTag,
        onTagCreate = viewModel::createTag,
        onDismiss = viewModel::toggleTagDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderFormScreen(
    reminder: Reminder,
    showSaveButton: StateFlow<Boolean>,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onToggleTagDialog: () -> Unit,
    onSetLocalDate: (LocalDate) -> Unit,
    onSetLocalTime: (LocalTime) -> Unit,
    onSetDate: () -> Unit,
    onSaveClick: () -> Unit,
) {
    val isSaveButtonVisible by showSaveButton.collectAsState()
    val simpleDateFormat by remember {
        mutableStateOf(SimpleDateFormat("dd MMM hh:mm a"))
    }
    val datePickerState = rememberMaterialDialogState()
    val timePickerState = rememberMaterialDialogState()
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it)
            onSaveClick()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = reminder.title,
            onValueChange = onTitleChanged,
            placeholder = { Text("Title") },
            colors = getTransparentTextFieldColors(),
            textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth()
        )

        TagRow(tags = reminder.tags, onToggleTagDialog = onToggleTagDialog)

        TextField(
            value = reminder.description,
            onValueChange = onDescriptionChanged,
            placeholder = { Text("Description") },
            colors = getTransparentTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )


        TextButton(onClick = datePickerState::show) {
            Text("Date: ${simpleDateFormat.format(reminder.date)}")
        }

        AnimatedVisibility(
            visible = isSaveButtonVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(onClick = { notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }) {
                Text("Save")
            }
        }

        MaterialDialog(
            dialogState = datePickerState,
            buttons = {
                positiveButton("Ok") {
                    timePickerState.show()
                }
                negativeButton("Cancel")
            }
        ) {
            datepicker {
                onSetLocalDate(it)
            }
        }

        MaterialDialog(
            dialogState = timePickerState,
            buttons = {
                positiveButton("Ok") {
                    onSetDate()
                }
                negativeButton("Cancel")
            }
        ) {
            timepicker {
                onSetLocalTime(it)
            }
        }
    }
}