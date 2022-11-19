package com.example.taskmanager.domain.models.reminder

import com.example.taskmanager.data.models.reminder.ReminderEntity
import com.example.taskmanager.domain.models.tag.Tag

data class Reminder(
    val reminderId: String,
    val title: String,
    val description: String,
    val date: Long,
    val tags: List<Tag>,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val isDeleted: Boolean,
    val isFavorite: Boolean,
    val createdAt: Long,
)

fun Reminder.asEntity() = ReminderEntity(
    title = title,
    description = description,
    date = date,
    isPinned = isPinned,
    isArchived = isArchived,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    reminderId = reminderId
)
