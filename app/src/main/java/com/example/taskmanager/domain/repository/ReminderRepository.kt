package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.reminder.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    fun getReminders(): Flow<List<Reminder>>

    fun getReminder(reminderId: String): Flow<Reminder?>

    suspend fun addReminder(reminder: Reminder): Resource<Unit>

    suspend fun updateReminder(reminder: Reminder): Resource<Unit>

    suspend fun deleteReminder(reminder: Reminder): Resource<Unit>

    suspend fun markReminderAsFavorite(reminderId: String, isFavorite: Boolean): Resource<Unit>

    suspend fun markReminderAsPinned(reminderId: String, isPinned: Boolean): Resource<Unit>

    suspend fun markReminderAsArchived(reminderId: String, isArchived: Boolean): Resource<Unit>

    suspend fun markReminderAsDeleted(reminderId: String, isDeleted: Boolean): Resource<Unit>

}