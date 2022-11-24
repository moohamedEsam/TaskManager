package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.room.NoteDao
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.data.models.note.NoteWithTagCrossRef
import com.example.taskmanager.data.models.note.NoteWithTagsEntity
import com.example.taskmanager.data.models.note.asDomain
import com.example.taskmanager.data.models.note.asNoteEntity
import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.domain.models.note.asEntity
import com.example.taskmanager.domain.repository.NoteRepository
import com.example.taskmanager.domain.utils.mapResultToResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<NoteWithTags>> = noteDao.getNotes()
        .map {
            it.map(NoteWithTagsEntity::asDomain)
        }

    override fun getArchivedNotes(): Flow<List<NoteWithTags>> = noteDao.getArchivedNotes()
        .map {
            it.map(NoteWithTagsEntity::asDomain)
        }

    override fun getNoteById(id: String): Flow<NoteWithTags?> = noteDao.getNoteById(id)
        .map {
            it?.asDomain()
        }

    override suspend fun addNote(note: NoteWithTags): Resource<Unit> = mapResultToResource {
        val noteWithTagsEntity = note.asEntity()
        noteDao.insertNote(noteWithTagsEntity.asNoteEntity())
        addNoteTag(noteWithTagsEntity)
    }

    override suspend fun updateNote(note: NoteWithTags): Resource<Unit> {
        return mapResultToResource {
            val noteWithTagsEntity = note.asEntity()
            noteDao.updateNote(noteWithTagsEntity.asNoteEntity())
            noteDao.deleteNoteTagsRefs(noteWithTagsEntity.noteId)
            addNoteTag(noteWithTagsEntity)
        }
    }

    override suspend fun updateNoteFavorite(noteId: String, isFavorite: Boolean): Resource<Unit> =
        mapResultToResource {
            noteDao.markNoteAsFavorite(noteId, isFavorite)
        }

    override suspend fun updateNotePinned(noteId: String, isPinned: Boolean): Resource<Unit> =
        mapResultToResource {
            noteDao.markNoteAsPinned(noteId, isPinned)
        }

    override suspend fun updateNoteArchived(noteId: String, isArchived: Boolean): Resource<Unit> =
        mapResultToResource {
            noteDao.markNoteAsArchived(noteId, isArchived)
        }

    override suspend fun updateNoteDeleted(noteId: String, isDeleted: Boolean): Resource<Unit> =
        mapResultToResource {
            noteDao.markNoteAsDeleted(noteId, isDeleted)
        }

    override suspend fun deleteNote(note: NoteWithTags): Resource<Unit> = mapResultToResource {
        noteDao.deleteNoteById(note.asEntity().asNoteEntity())
    }

    private suspend fun addNoteTag(note: NoteWithTagsEntity) {
        note.tags.forEach { tag ->
            noteDao.addTagToNote(NoteWithTagCrossRef(note.noteId, tag.tagId))
        }
    }
}