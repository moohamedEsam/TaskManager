package com.example.taskmanager.domain.models

import com.example.taskmanager.data.models.TagWithNotesEntity

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
