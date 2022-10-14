package com.example.taskmanager.dataModels

import androidx.room.*
import com.example.taskmanager.domain.dataModels.NoteWithTags


data class NoteWithTagsDto(
    @Embedded
    override val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    override val tags: List<TagEntity>
):NoteWithTags