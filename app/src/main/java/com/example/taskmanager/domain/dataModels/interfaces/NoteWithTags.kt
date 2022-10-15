package com.example.taskmanager.domain.dataModels.interfaces

interface NoteWithTags : Note {
    val tags: List<Tag>
}