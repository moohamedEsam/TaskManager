package com.example.taskmanager.domain.dataModels

interface TagWithNotes {
    val notes: List<Note>
    val tag: Tag
}