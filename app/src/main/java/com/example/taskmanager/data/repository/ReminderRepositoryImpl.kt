package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.room.ReminderDao
import com.example.taskmanager.data.models.reminder.ReminderWithTagCrossRef
import com.example.taskmanager.data.models.reminder.asDomain
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.domain.models.reminder.asEntity
import com.example.taskmanager.domain.repository.ReminderRepository
import com.example.taskmanager.domain.utils.mapResultToResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepositoryImpl(private val reminderDao: ReminderDao) : ReminderRepository {
    override fun getReminders(): Flow<List<Reminder>> =
        reminderDao.getReminders().map { reminders ->
            reminders.map { reminder ->
                reminder.asDomain()
            }
        }

    override fun getReminder(reminderId: String): Flow<Reminder?> =
        reminderDao.getReminderById(reminderId).map { reminder ->
            reminder?.asDomain()
        }

    override suspend fun addReminder(reminder: Reminder): Resource<Unit> = mapResultToResource {
        reminderDao.insert(reminder.asEntity())
        reminder.tags.forEach {
            reminderDao.addReminderTag(ReminderWithTagCrossRef(reminder.reminderId, it.tagId))
        }
    }

    override suspend fun updateReminder(reminder: Reminder): Resource<Unit> = mapResultToResource {
        reminderDao.update(reminder.asEntity())
        reminderDao.deleteReminderTags(reminder.reminderId)
        reminder.tags.forEach {
            reminderDao.addReminderTag(ReminderWithTagCrossRef(reminder.reminderId, it.tagId))
        }
    }

    override suspend fun deleteReminder(reminder: Reminder): Resource<Unit> = mapResultToResource {
        reminderDao.deleteReminderTags(reminder.reminderId)
        reminderDao.delete(reminder.asEntity())
    }

    override suspend fun markReminderAsFavorite(
        reminderId: String,
        isFavorite: Boolean
    ): Resource<Unit> = mapResultToResource {
        reminderDao.markReminderAsFavorite(reminderId, isFavorite)
    }

    override suspend fun markReminderAsPinned(
        reminderId: String,
        isPinned: Boolean
    ): Resource<Unit> = mapResultToResource {
        reminderDao.markReminderAsPinned(reminderId, isPinned)
    }

    override suspend fun markReminderAsArchived(
        reminderId: String,
        isArchived: Boolean
    ): Resource<Unit> = mapResultToResource {
        reminderDao.markReminderAsArchived(reminderId, isArchived)
    }

    override suspend fun markReminderAsDeleted(
        reminderId: String,
        isDeleted: Boolean
    ): Resource<Unit> = mapResultToResource {
        reminderDao.markReminderAsDeleted(reminderId, isDeleted)
    }
}