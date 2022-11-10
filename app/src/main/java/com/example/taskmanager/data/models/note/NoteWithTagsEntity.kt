package com.example.taskmanager.data.models.note

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.data.models.tag.TagEntity
import com.example.taskmanager.data.models.tag.asDomain
import com.example.taskmanager.domain.models.note.Attachment
import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.presentation.utils.noteBody.NoteBody

data class NoteWithTagsEntity(
    val title: String,
    val body: List<NoteBody>,
    val attachments: List<Attachment>,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    val tags: List<TagEntity>,
    val isFavorite: Boolean,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val isDeleted: Boolean,
    val lastEditDate: Long?,
    val creationDate: Long,
    val noteId: String
)

fun NoteWithTagsEntity.asDomain() = NoteWithTags(
    title = title,
    body = body,
    attachments = attachments,
    tags = tags.map { it.asDomain() },
    lastEditDate = lastEditDate,
    creationDate = creationDate,
    isDeleted = isDeleted,
    isArchived = isArchived,
    isPinned = isPinned,
    isFavorite = isFavorite,
    noteId = noteId
)

fun NoteWithTagsEntity.asNoteEntity() = NoteEntity(
    title = title,
    body = body,
    attachments = attachments,
    isDeleted = isDeleted,
    isArchived = isArchived,
    isPinned = isPinned,
    isFavorite = isFavorite,
    lastEditDate = lastEditDate,
    creationDate = creationDate,
    noteId = noteId
)
