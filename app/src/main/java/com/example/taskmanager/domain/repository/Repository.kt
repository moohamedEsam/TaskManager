package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.dataModels.data.NoteWithTagsEntity
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.data.TagEntity
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNotes(): Flow<List<NoteWithTagsEntity>>
    fun getNoteById(id: String): Flow<NoteWithTagsEntity?>
    suspend fun addNote(note: NoteWithTagsEntity): Resource<Unit>
    suspend fun addTag(tag: TagEntity): Resource<Unit>
    suspend fun updateNote(note: NoteWithTagsEntity): Resource<Unit>
    suspend fun updateNoteFavorite(noteId: String, isFavorite: Boolean): Resource<Unit>
    suspend fun updateNotePinned(noteId: String, isPinned: Boolean): Resource<Unit>
    suspend fun updateNoteArchived(noteId: String, isArchived: Boolean): Resource<Unit>
    suspend fun updateNoteDeleted(noteId: String, isDeleted: Boolean): Resource<Unit>
    suspend fun deleteNote(note: NoteWithTagsEntity): Resource<Unit>
}