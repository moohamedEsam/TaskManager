package com.example.taskmanager.data.models.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reminder")
data class ReminderEntity(
    val title: String,
    val description: String,
    val date: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val isFavorite: Boolean = false,
    @PrimaryKey
    val reminderId: String = UUID.randomUUID().toString(),
)
