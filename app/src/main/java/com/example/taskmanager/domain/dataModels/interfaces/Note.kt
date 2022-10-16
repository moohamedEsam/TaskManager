package com.example.taskmanager.domain.dataModels.interfaces

interface Note {
    val title: String
    val description: String
    val attachments: List<Attachment>
    val noteId: String
    val creationDate: Long
    val lastEditDate: Long
    val isDeleted: Boolean
    val isArchived: Boolean
    val isPinned: Boolean
    val isFavorite: Boolean
}
