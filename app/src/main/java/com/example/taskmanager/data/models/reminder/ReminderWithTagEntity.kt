package com.example.taskmanager.data.models.reminder

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.data.models.tag.TagEntity
import com.example.taskmanager.data.models.tag.asDomain
import com.example.taskmanager.domain.models.reminder.Reminder
import java.util.*

data class ReminderWithTagEntity(
    val title: String,
    val description: String,
    val date: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isDone: Boolean = false,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val isFavorite: Boolean = false,
    val reminderId: String = UUID.randomUUID().toString(),
    @Relation(
        parentColumn = "reminderId",
        entityColumn = "tagId",
        associateBy = Junction(ReminderWithTagCrossRef::class)
    )
    val tags: List<TagEntity>
)

fun ReminderWithTagEntity.asDomain() = Reminder(
    title = title,
    description = description,
    date = date,
    isDone = isDone,
    isPinned = isPinned,
    isArchived = isArchived,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    reminderId = reminderId,
    tags = tags.map { it.asDomain() }
)

fun ReminderWithTagEntity.asReminderEntity() = ReminderEntity(
    title = title,
    description = description,
    date = date,
    isPinned = isPinned,
    isArchived = isArchived,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    reminderId = reminderId
)