package com.example.taskmanager.domain.dataModels.interfaces

interface TagWithNotes: Tag {
    val notes: List<Note>
}