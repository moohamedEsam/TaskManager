package com.example.taskmanager.presentation.screens.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.domain.usecase.reminder.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RemindersViewModel(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val updateReminderPinUseCase: UpdateReminderPinUseCase,
    private val updateReminderFavoriteUseCase: UpdateReminderFavoriteUseCase,
    private val updateReminderArchiveUseCase: UpdateReminderArchiveUseCase,
    private val updateReminderDeleteUseCase: UpdateReminderDeleteUseCase
) : ViewModel() {
    private val _reminders = MutableStateFlow(emptyList<Reminder>())
    val reminders = _reminders.asStateFlow()

    init {
        getReminders()
    }

    private fun getReminders() = viewModelScope.launch {
        getRemindersUseCase().collectLatest {
            _reminders.update { _ -> it }
        }
    }

    fun updateReminderPin(reminder: Reminder) = viewModelScope.launch {
        updateReminderPinUseCase(reminder.reminderId, !reminder.isPinned)
    }

    fun updateReminderFavorite(reminder: Reminder) = viewModelScope.launch {
        updateReminderFavoriteUseCase(reminder.reminderId, !reminder.isFavorite)
    }

    fun updateReminderArchive(reminder: Reminder) = viewModelScope.launch {
        updateReminderArchiveUseCase(reminder.reminderId, !reminder.isArchived)
    }

    fun updateReminderDelete(reminder: Reminder) = viewModelScope.launch {
        updateReminderDeleteUseCase(reminder.reminderId, !reminder.isDeleted)
    }
}