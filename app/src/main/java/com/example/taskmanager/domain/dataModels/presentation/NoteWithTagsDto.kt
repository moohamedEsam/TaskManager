package com.example.taskmanager.domain.dataModels.presentation

import com.example.taskmanager.domain.dataModels.interfaces.Attachment
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import com.example.taskmanager.domain.dataModels.interfaces.Tag

data class NoteWithTagsDto(
    override val title: String = "",
    override val description: String = "",
    override val attachments: List<Attachment> = emptyList(),
    override val tags: List<Tag> = emptyList(),
    override val noteId: String = ""
) : NoteWithTags
