package com.example.taskmanager.domain.models.reminder

import com.example.taskmanager.data.models.reminder.ReminderEntity
import com.example.taskmanager.domain.models.tag.Tag

data class Reminder(
    val reminderId: String,
    val title: String,
    val description: String,
    val date: Long,
    val isDone: Boolean,
    val tags: List<Tag>,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val isDeleted: Boolean,
    val isFavorite: Boolean,
)

fun Reminder.asEntity() = ReminderEntity(
    title = title,
    description = description,
    date = date,
    isDone = isDone,
    isPinned = isPinned,
    isArchived = isArchived,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    reminderId = reminderId
)
