package com.example.taskmanager.domain.dataModels.presentation

import com.example.taskmanager.domain.dataModels.data.NoteEntity
import com.example.taskmanager.domain.dataModels.data.NoteWithTagsEntity
import com.example.taskmanager.domain.dataModels.interfaces.Attachment
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import com.example.taskmanager.domain.dataModels.interfaces.Tag
import com.example.taskmanager.presentation.utils.noteBody.NoteBody
import java.util.*

data class NoteWithTagsDto(
    override val title: String = "",
    override val body: List<NoteBody> = emptyList(),
    override val attachments: List<Attachment> = emptyList(),
    override val tags: List<TagDto> = emptyList(),
    override val lastEditDate: Long = Date().time,
    override val creationDate: Long = Date().time,
    override val isDeleted: Boolean = false,
    override val isArchived: Boolean = false,
    override val isPinned: Boolean = false,
    override val isFavorite: Boolean = false,
    override val noteId: String = UUID.randomUUID().toString()
) : NoteWithTags {
    fun toEntity() = NoteWithTagsEntity(
        title = title,
        body = body,
        attachments = attachments,
        tags = tags.map { it.toEntity() },
        isFavorite = isFavorite,
        isPinned = isPinned,
        isArchived = isArchived,
        isDeleted = isDeleted,
        lastEditDate = lastEditDate,
        creationDate = creationDate,
        noteId = noteId
    )
}
