package com.example.taskmanager.domain.dataModels.interfaces

import com.example.taskmanager.presentation.utils.noteBody.NoteBody

interface Note {
    val title: String
    val body:List<NoteBody>
    val attachments: List<Attachment>
    val noteId: String
    val creationDate: Long
    val lastEditDate: Long
    val isDeleted: Boolean
    val isArchived: Boolean
    val isPinned: Boolean
    val isFavorite: Boolean
}
