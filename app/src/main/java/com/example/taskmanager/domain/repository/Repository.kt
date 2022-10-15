package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.dataModels.data.NoteWithTagsEntity
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.data.TagEntity
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNotes(): Flow<List<NoteWithTags>>
    fun getNoteById(id: String): Flow<NoteWithTags?>
    suspend fun addNote(note: NoteWithTagsEntity): Resource<Unit>
    suspend fun addTag(tag: TagEntity): Resource<Unit>
    suspend fun updateNote(note: NoteWithTagsEntity): Resource<Unit>
    suspend fun deleteNote(note: NoteWithTagsEntity): Resource<Unit>
}