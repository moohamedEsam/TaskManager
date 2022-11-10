package com.example.taskmanager.data.models.tag

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.data.models.note.NoteEntity
import com.example.taskmanager.data.models.note.NoteWithTagCrossRef
import com.example.taskmanager.data.models.note.asDomain
import com.example.taskmanager.domain.models.tag.TagWithNotes


data class TagWithNotesEntity(
    val tagId: String,
    val name: String,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "noteId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    val notes: List<NoteEntity>
)

fun TagWithNotesEntity.asDomain() = TagWithNotes(notes.map { it.asDomain() }, name, tagId)
