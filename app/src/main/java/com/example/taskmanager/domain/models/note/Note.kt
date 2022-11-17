package com.example.taskmanager.domain.models.note

import com.example.taskmanager.data.models.note.NoteEntity
import com.example.taskmanager.presentation.utils.noteBody.NoteBody

data class Note(
    val body: List<NoteBody>,
    val attachments: List<Attachment>,
    val isFavorite: Boolean,
    val creationDate: Long,
    val isDeleted: Boolean,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val noteId: String,
    val title: String
)

fun Note.asEntity() = NoteEntity(
    title = title,
    body = body,
    attachments = attachments,
    isDeleted = isDeleted,
    isArchived = isArchived,
    isPinned = isPinned,
    isFavorite = isFavorite,
    creationDate = creationDate,
    noteId = noteId
)
