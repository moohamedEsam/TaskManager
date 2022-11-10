package com.example.taskmanager.data.models.note

import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "tagId"], tableName = "noteWithTagCrossRef")
data class NoteWithTagCrossRef(
    val noteId: String,
    val tagId: String
)
