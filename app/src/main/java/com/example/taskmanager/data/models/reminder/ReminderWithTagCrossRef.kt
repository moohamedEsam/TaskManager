package com.example.taskmanager.data.models.reminder

import androidx.room.Entity

@Entity(primaryKeys = ["reminderId", "tagId"], tableName = "reminderWithTagCrossRef")
data class ReminderWithTagCrossRef(
    val reminderId: String,
    val tagId: String
)
