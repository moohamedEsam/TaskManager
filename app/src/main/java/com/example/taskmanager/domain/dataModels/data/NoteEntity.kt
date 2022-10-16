package com.example.taskmanager.domain.dataModels.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.dataModels.interfaces.Attachment
import com.example.taskmanager.domain.dataModels.interfaces.Note
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    override val title: String = "",
    override val description: String = "",
    override val attachments: List<Attachment> = emptyList(),
    override val isDeleted: Boolean = false,
    override val isArchived: Boolean = false,
    override val isPinned: Boolean = false,
    override val isFavorite: Boolean = false,
    override val lastEditDate: Long = Date().time,
    override val creationDate: Long = Date().time,
    @PrimaryKey
    override val noteId: String = UUID.randomUUID().toString()
) : Note