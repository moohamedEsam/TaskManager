package com.example.taskmanager.domain.dataModels.data

import androidx.room.Junction
import androidx.room.Relation
import com.example.taskmanager.domain.dataModels.interfaces.TagWithNotes
import com.example.taskmanager.domain.dataModels.presentation.TagWithNotesDto


data class TagWithNotesEntity(
    override val tagId: String,
    override val name: String,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "noteId",
        associateBy = Junction(NoteWithTagCrossRef::class)
    )
    override val notes: List<NoteEntity>
) : TagWithNotes {
    fun toDomain() = TagWithNotesDto(notes.map { it.toDomain() }, name, tagId)
}