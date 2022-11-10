package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<NoteWithTags>>
    fun getNoteById(id: String): Flow<NoteWithTags?>
    suspend fun addNote(note: NoteWithTags): Resource<Unit>
    suspend fun updateNote(note: NoteWithTags): Resource<Unit>
    suspend fun updateNoteFavorite(noteId: String, isFavorite: Boolean): Resource<Unit>
    suspend fun updateNotePinned(noteId: String, isPinned: Boolean): Resource<Unit>
    suspend fun updateNoteArchived(noteId: String, isArchived: Boolean): Resource<Unit>
    suspend fun updateNoteDeleted(noteId: String, isDeleted: Boolean): Resource<Unit>
    suspend fun deleteNote(note: NoteWithTags): Resource<Unit>
}