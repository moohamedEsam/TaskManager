package com.example.taskmanager.dataModels

import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "tagId"], tableName = "noteWithTagCrossRef")
data class NoteWithTagCrossRef(
    val noteId: String,
    val tagId: String
)
