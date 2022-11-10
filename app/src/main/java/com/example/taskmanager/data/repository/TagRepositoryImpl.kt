package com.example.taskmanager.data.repository

import com.example.taskmanager.data.local.room.TagDao
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.data.models.tag.TagEntity
import com.example.taskmanager.data.models.note.asDomain
import com.example.taskmanager.data.models.tag.asDomain
import com.example.taskmanager.domain.models.tag.Tag
import com.example.taskmanager.domain.models.tag.asEntity
import com.example.taskmanager.domain.repository.TagRepository
import com.example.taskmanager.domain.utils.mapResultToResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(private val tagDao: TagDao) : TagRepository {
    override suspend fun addTag(tag: Tag): Resource<Unit> = mapResultToResource {
        tagDao.insertTag(tag.asEntity())
    }

    override suspend fun updateTag(tag: Tag): Resource<Unit> = mapResultToResource {
        tagDao.updateTag(tag.asEntity())
    }

    override suspend fun deleteTag(tag: Tag): Resource<Unit> = mapResultToResource {
        tagDao.deleteTag(tag.asEntity())
    }

    override fun getTags(): Flow<List<Tag>> = tagDao.getTags().map {
        it.map(TagEntity::asDomain)
    }

}