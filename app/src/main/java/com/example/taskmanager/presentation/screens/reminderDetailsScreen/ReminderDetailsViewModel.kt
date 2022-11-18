package com.example.taskmanager.presentation.screens.reminderDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.domain.usecase.reminder.GetReminderUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReminderDetailsViewModel(
    private val reminderId: String,
    private val getReminderUseCase: GetReminderUseCase,
) : ViewModel() {
    private val _reminder = MutableStateFlow<Resource<Reminder>>(Resource.Initial())
    val reminder = _reminder.asStateFlow()

    init {
        viewModelScope.launch {
            getReminderUseCase(reminderId).collectLatest {
                _reminder.update { _ ->
                    if(it == null)
                        Resource.Error("Reminder not found")
                    else
                        Resource.Success(it)
                }
            }
        }
    }
}