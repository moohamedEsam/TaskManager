package com.example.taskmanager.domain.models

import com.example.taskmanager.data.models.NoteWithTagsEntity
import com.example.taskmanager.presentation.utils.noteBody.NoteBody
import java.util.*

data class NoteWithTags(
    val title: String,
    val body: List<NoteBody>,
    val attachments: List<Attachment>,
    val tags: List<Tag>,
    val lastEditDate: Long? = null,
    val creationDate: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false,
    val isArchived: Boolean = false,
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val noteId: String = UUID.randomUUID().toString()
) {
    companion object
}

fun NoteWithTags.asEntity() = NoteWithTagsEntity(
    title = title,
    body = body,
    attachments = attachments,
    tags = tags.map { it.asEntity() },
    isFavorite = isFavorite,
    isPinned = isPinned,
    isArchived = isArchived,
    isDeleted = isDeleted,
    lastEditDate = lastEditDate,
    creationDate = creationDate,
    noteId = noteId
)

fun NoteWithTags.Companion.empty() = NoteWithTags(
    title = "",
    body = listOf(),
    attachments = listOf(),
    tags = listOf(),
    lastEditDate = 0,
    creationDate = 0,
    isDeleted = false,
    isArchived = false,
    isPinned = false,
    isFavorite = false,
    noteId = ""
)