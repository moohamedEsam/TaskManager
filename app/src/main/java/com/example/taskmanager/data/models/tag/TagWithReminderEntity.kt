package com.example.taskmanager.data.models.tag

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.data.models.reminder.ReminderEntity
import com.example.taskmanager.data.models.reminder.ReminderWithTagCrossRef
import java.util.*

data class TagWithReminderEntity(
    val name: String,
    val tagId: String = UUID.randomUUID().toString(),
    @Relation(
        parentColumn = "tagId",
        entityColumn = "reminderId",
        associateBy = Junction(ReminderWithTagCrossRef::class)
    )
    val reminders: List<ReminderEntity>
)
