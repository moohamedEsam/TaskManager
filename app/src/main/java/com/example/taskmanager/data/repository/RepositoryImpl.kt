package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.room.NoteDao
import com.example.taskmanager.data.local.room.TagDao
import com.example.taskmanager.dataModels.NoteWithTagCrossRef
import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.Resource
import com.example.taskmanager.dataModels.TagEntity
import com.example.taskmanager.domain.dataModels.NoteWithTags
import com.example.taskmanager.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val noteDao: NoteDao, private val tagDao: TagDao) : Repository {
    private suspend fun <T> mapResultToResource(result: suspend () -> T) = try {
        Resource.Success(result())
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown error")
    }

    override suspend fun getNotes(): Flow<List<NoteWithTags>> = noteDao.getNotes()

    override suspend fun getNoteById(id: String): Flow<NoteWithTags?> = noteDao.getNoteById(id)

    override suspend fun addTag(tag: TagEntity): Resource<Unit> = mapResultToResource {
        tagDao.insertTag(tag)
    }

    override suspend fun addNote(noteWithTagsDto: NoteWithTagsDto): Resource<Unit> =
        mapResultToResource {
            noteDao.insertNote(noteWithTagsDto.note)
            addNoteTag(noteWithTagsDto)
        }

    override suspend fun updateNote(noteWithTagsDto: NoteWithTagsDto) = mapResultToResource {
        noteDao.updateNote(noteWithTagsDto.note)
        noteDao.deleteNoteTags(noteWithTagsDto.note.noteId)
        addNoteTag(noteWithTagsDto)
    }

    override suspend fun deleteNote(noteWithTagsDto: NoteWithTagsDto): Resource<Unit> =
        mapResultToResource {
            noteDao.deleteNoteById(noteWithTagsDto.note)
            noteDao.deleteNoteTags(noteWithTagsDto.note.noteId)
        }

    private suspend fun addNoteTag(noteWithTagsDto: NoteWithTagsDto) {
        noteWithTagsDto.tags.forEach { tag ->
            if (tagDao.getTagSnapShotById(tag.tagId) == null)
                tagDao.insertTag(tag)

            noteDao.addTagToNote(NoteWithTagCrossRef(noteWithTagsDto.note.noteId, tag.tagId))
        }
    }
}