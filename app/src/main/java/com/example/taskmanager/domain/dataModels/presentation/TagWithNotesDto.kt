package com.example.taskmanager.domain.dataModels.presentation

import com.example.taskmanager.domain.dataModels.data.TagWithNotesEntity
import com.example.taskmanager.domain.dataModels.interfaces.Note
import com.example.taskmanager.domain.dataModels.interfaces.TagWithNotes

data class TagWithNotesDto(
    override val notes: List<NoteDto> = emptyList(),
    override val name: String = "",
    override val tagId: String = "",
) : TagWithNotes {
    fun toEntity() = TagWithNotesEntity(tagId, name, notes.map { it.toEntity() })
}
