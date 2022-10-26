package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.room.NoteDao
import com.example.taskmanager.data.local.room.TagDao
import com.example.taskmanager.domain.dataModels.data.NoteWithTagCrossRef
import com.example.taskmanager.domain.dataModels.data.NoteWithTagsEntity
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.data.TagEntity
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import com.example.taskmanager.domain.repository.Repository
import dev.krud.shapeshift.ShapeShift
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val noteDao: NoteDao,
    private val tagDao: TagDao,
    private val shapeShift: ShapeShift
) : Repository {
    private suspend fun <T> mapResultToResource(result: suspend () -> T) = try {
        Resource.Success(result())
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown error")
    }

    override suspend fun updateNoteFavorite(noteId: String, isFavorite: Boolean): Resource<Unit> =
        mapResultToResource { noteDao.markNoteAsFavorite(noteId, isFavorite) }

    override suspend fun updateNotePinned(noteId: String, isPinned: Boolean): Resource<Unit> =
        mapResultToResource { noteDao.markNoteAsPinned(noteId, isPinned) }

    override suspend fun updateNoteArchived(noteId: String, isArchived: Boolean): Resource<Unit> =
        mapResultToResource { noteDao.markNoteAsArchived(noteId, isArchived) }

    override suspend fun updateNoteDeleted(noteId: String, isDeleted: Boolean): Resource<Unit> =
        mapResultToResource { noteDao.markNoteAsDeleted(noteId, isDeleted) }

    override fun getNotes(): Flow<List<NoteWithTags>> = noteDao.getNotes()

    override fun getNoteById(id: String): Flow<NoteWithTags?> = noteDao.getNoteById(id)

    override suspend fun addTag(tag: TagEntity): Resource<Unit> = mapResultToResource {
        tagDao.insertTag(tag)
    }

    override suspend fun addNote(note: NoteWithTagsEntity): Resource<Unit> =
        mapResultToResource {
            noteDao.insertNote(shapeShift.map(note))
            addNoteTag(note)
        }

    override suspend fun updateNote(note: NoteWithTagsEntity) = mapResultToResource {
        noteDao.updateNote(shapeShift.map(note))
        noteDao.deleteNoteTags(note.noteId)
        addNoteTag(note)
    }

    override suspend fun deleteNote(note: NoteWithTagsEntity): Resource<Unit> =
        mapResultToResource {
            noteDao.deleteNoteById(shapeShift.map(note))
            noteDao.deleteNoteTags(note.noteId)
        }

    private suspend fun addNoteTag(note: NoteWithTagsEntity) {
        note.tags.forEach { tag ->
            if (tagDao.getTagSnapShotById(tag.tagId) == null)
                tagDao.insertTag(tag)

            noteDao.addTagToNote(NoteWithTagCrossRef(note.noteId, tag.tagId))
        }
    }
}