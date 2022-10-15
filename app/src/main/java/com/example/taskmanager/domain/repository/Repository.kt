package com.example.taskmanager.domain.repository

import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.Resource
import com.example.taskmanager.dataModels.TagEntity
import com.example.taskmanager.domain.dataModels.NoteWithTags
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getNotes(): Flow<List<NoteWithTags>>
    suspend fun getNoteById(id: String): Flow<NoteWithTags?>
    suspend fun addNote(noteWithTagsDto: NoteWithTagsDto):Resource<Unit>
    suspend fun addTag(tag: TagEntity): Resource<Unit>
    suspend fun updateNote(noteWithTagsDto: NoteWithTagsDto): Resource<Unit>
    suspend fun deleteNote(noteWithTagsDto: NoteWithTagsDto): Resource<Unit>
}