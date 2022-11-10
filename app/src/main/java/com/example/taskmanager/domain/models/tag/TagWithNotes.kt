package com.example.taskmanager.domain.models.tag

import com.example.taskmanager.data.models.tag.TagWithNotesEntity
import com.example.taskmanager.domain.models.note.Note
import com.example.taskmanager.domain.models.note.asEntity

data class TagWithNotes(
    val notes: List<Note>,
    val name: String,
    val tagId: String,
)

fun TagWithNotes.asEntity() = TagWithNotesEntity(
    tagId = tagId,
    name = name,
    notes = notes.map(Note::asEntity)
)
