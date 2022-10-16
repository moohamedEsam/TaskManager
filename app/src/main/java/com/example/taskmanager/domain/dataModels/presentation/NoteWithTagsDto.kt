package com.example.taskmanager.domain.dataModels.presentation

import com.example.taskmanager.domain.dataModels.interfaces.Attachment
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import com.example.taskmanager.domain.dataModels.interfaces.Tag
import java.util.*

data class NoteWithTagsDto(
    override val title: String = "",
    override val description: String = "",
    override val attachments: List<Attachment> = emptyList(),
    override val tags: List<Tag> = emptyList(),
    override val lastEditDate: Long = Date().time,
    override val creationDate: Long = Date().time,
    override val isDeleted: Boolean = false,
    override val isArchived: Boolean = false,
    override val isPinned: Boolean = false,
    override val isFavorite: Boolean = false,
    override val noteId: String = UUID.randomUUID().toString()
) : NoteWithTags
