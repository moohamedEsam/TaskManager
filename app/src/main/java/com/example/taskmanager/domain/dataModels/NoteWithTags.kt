package com.example.taskmanager.domain.dataModels

interface NoteWithTags {
    val note: Note
    val tags: List<Tag>
}