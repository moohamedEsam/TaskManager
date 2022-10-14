package com.example.taskmanager.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.dataModels.Attachment
import com.example.taskmanager.domain.dataModels.Note
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    override val title: String,
    override val description: String,
    override val attachments: List<Attachment>,
    @PrimaryKey
    override val noteId: String = UUID.randomUUID().toString()
) : Note