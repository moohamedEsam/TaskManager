package com.example.taskmanager.domain.usecase.reminder

import com.example.taskmanager.domain.models.reminder.Reminder
import kotlinx.coroutines.flow.Flow

fun interface GetReminderUseCase : (String) -> Flow<Reminder?>

fun interface GetRemindersUseCase : () -> Flow<List<Reminder>>