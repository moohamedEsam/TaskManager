package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.data.models.TagEntity
import com.example.taskmanager.domain.models.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getTags(): Flow<List<Tag>>
    suspend fun addTag(tag: Tag): Resource<Unit>
    suspend fun updateTag(tag: Tag): Resource<Unit>
    suspend fun deleteTag(tag: Tag): Resource<Unit>

}