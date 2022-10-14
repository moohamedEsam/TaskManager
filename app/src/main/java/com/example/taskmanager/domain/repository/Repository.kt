package com.example.taskmanager.domain.repository

import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.TagEntity
import com.example.taskmanager.domain.dataModels.NoteWithTags
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNotes(): Flow<List<NoteWithTags>>
    fun getNoteById(id: String): Flow<NoteWithTags?>
    suspend fun addNote(noteWithTagsDto: NoteWithTagsDto)
    suspend fun addTag(tag: TagEntity)
    suspend fun updateNote(noteWithTagsDto: NoteWithTagsDto)
    suspend fun deleteNote(noteWithTagsDto: NoteWithTagsDto)
}