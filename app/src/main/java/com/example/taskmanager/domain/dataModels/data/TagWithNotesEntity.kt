package com.example.taskmanager.domain.dataModels.data

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.domain.dataModels.interfaces.TagWithNotes


data class TagWithNotesEntity(
    override val tagId: String,
    override val name: String,
    override val color: Int,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "noteId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    override val notes: List<NoteEntity>
) : TagWithNotes