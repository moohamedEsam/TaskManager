package com.example.taskmanager.domain.dataModels.presentation

import com.example.taskmanager.domain.dataModels.interfaces.Note
import com.example.taskmanager.domain.dataModels.interfaces.TagWithNotes

data class TagWithNotesDto(
    override val notes: List<Note> = emptyList(),
    override val name: String = "",
    override val color: Int = 0,
    override val tagId: String = "",
) : TagWithNotes
