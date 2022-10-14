package com.example.taskmanager.dataModels

import androidx.room.*
import com.example.taskmanager.domain.dataModels.Note
import com.example.taskmanager.domain.dataModels.NoteWithTags
import com.example.taskmanager.domain.dataModels.TagWithNotes


data class TagWithNotesDto(
    @Embedded
    override val tag: TagEntity,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "noteId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    override val notes: List<NoteEntity>
) : TagWithNotes