package com.example.taskmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.models.Attachment
import com.example.taskmanager.domain.models.Note
import com.example.taskmanager.presentation.utils.noteBody.NoteBody
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    val title: String,
    val body: List<NoteBody>,
    val attachments: List<Attachment>,
    val isDeleted: Boolean,
    val isArchived: Boolean,
    val isPinned: Boolean,
    val isFavorite: Boolean,
    val lastEditDate: Long?,
    val creationDate: Long,

    @PrimaryKey
    val noteId: String = UUID.randomUUID().toString()
)

fun NoteEntity.asDomain() = Note(
    body = body,
    attachments = attachments,
    isFavorite = isFavorite,
    creationDate = creationDate,
    isDeleted = isDeleted,
    isPinned = isPinned,
    lastEditDate = lastEditDate,
    isArchived = isArchived,
    noteId = noteId,
    title = title
)
