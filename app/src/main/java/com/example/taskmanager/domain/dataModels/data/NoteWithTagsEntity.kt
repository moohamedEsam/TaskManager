package com.example.taskmanager.domain.dataModels.data

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.domain.dataModels.interfaces.Attachment
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import java.util.UUID

data class NoteWithTagsEntity(
    override val title: String = "",
    override val description: String = "",
    override val attachments: List<Attachment> = emptyList(),
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    override val tags: List<TagEntity> = emptyList(),
    override val isFavorite: Boolean = false,
    override val isPinned: Boolean = false,
    override val isArchived: Boolean = false,
    override val isDeleted: Boolean = false,
    override val lastEditDate: Long = 0,
    override val creationDate: Long = 0,
    override val noteId: String = UUID.randomUUID().toString()
) : NoteWithTags
