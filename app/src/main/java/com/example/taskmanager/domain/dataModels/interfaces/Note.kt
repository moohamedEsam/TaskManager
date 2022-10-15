package com.example.taskmanager.domain.dataModels.interfaces

interface Note {
    val title: String
    val description: String
    val attachments: List<Attachment>
    val noteId: String
}
